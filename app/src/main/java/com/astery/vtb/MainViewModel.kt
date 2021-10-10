package com.astery.vtb

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astery.copying.forms_adapter.utils.JobListener
import com.astery.vtb.local_storage.rx_utils.Repository
import com.astery.vtb.model.*
import com.astery.vtb.repository.commands.Command
import com.astery.vtb.repository.commands.CommandManager
import com.astery.vtb.repository.commands.TogetherCommandManager
import com.astery.vtb.repository.preferences.TransportPreferences
import io.reactivex.observers.DisposableSingleObserver
import java.util.*
import kotlin.random.Random

class MainViewModel:ViewModel() {
    val money: MutableLiveData<Int>  = MutableLiveData()
    val happiness:MutableLiveData<Int> = MutableLiveData()
    val iteration:MutableLiveData<Int> = MutableLiveData()

    val r:Random = Random(Calendar.getInstance().get(Calendar.SECOND))

    @SuppressLint("StaticFieldLeak")
    private lateinit var context:Context
    private lateinit var repository: Repository

    fun setup(context: Context, repository: Repository){
        this.context = context
        this.repository = repository

        setup()
    }

    fun setup(){
        money.value = TransportPreferences.getValue(context, GameValue.money)
        happiness.value = TransportPreferences.getValue(context, GameValue.hapiness)
        iteration.value = TransportPreferences.getValue(context, GameValue.iteration)
    }

    fun endDay(listener: JobListener){
        val cm2 = TogetherCommandManager()
        val command = Command { updateProducts(cm2, it) }
        command.next = cm2.command
        command.next = Command{updateLife(it)}
        command.next = Command{resetDays(it)}
        command.next = Command{setup(); it.done(true)}
        command.setParentListener(listener)
        command.work()
    }

    fun endGame(listener: JobListener){
        if (iteration.value == 12){
            TransportPreferences.setValue(context, GameValue.iteration, 13)
            listener.done(true)
        } else
            listener.done(false)
    }

    private fun updateProducts(cm2:TogetherCommandManager, it:JobListener){
        repository.getAllActions(object : DisposableSingleObserver<List<ActionEntity>>() {
            override fun onSuccess(t: List<ActionEntity>) {
                for (i in t) {
                    setActualPrice(i)
                    cm2.setCommand {
                        repository.loadValue(i, it) }
                }
                it.done(true)
            }

            override fun onError(e: Throwable) {}
        })
    }

    private fun updateLife(it:JobListener){
        TransportPreferences.setValue(context, GameValue.money,
            salary+TransportPreferences.getValue(context, GameValue.money))

        it.done(true)
    }

    private fun resetDays(it:JobListener){
        TransportPreferences.setValue(context, GameValue.iteration,
            1+TransportPreferences.getValue(context, GameValue.iteration))

        it.done(true)
    }

    fun resetEverything(){
        TransportPreferences.setValue(context, GameValue.money, salary)
        TransportPreferences.setValue(context, GameValue.hapiness, 0)
        TransportPreferences.setValue(context, GameValue.iteration, 1)
        TransportPreferences.setValue(context, GameValue.level, 0)

        repository.getAllActions(object : DisposableSingleObserver<List<ActionEntity>>() {
            override fun onSuccess(t: List<ActionEntity>) {
                for (i in t) {
                    i.actualPrice = i.startPrice.toFloat()
                    i.oldPrice = 0f
                    repository.loadValue(i, null)
                }
            }

            override fun onError(e: Throwable) {}
        })

        repository.getInvestHistory(object: DisposableSingleObserver<List<InvestHistory>>() {
            override fun onSuccess(t: List<InvestHistory>) {
                for (i in t)
                    repository.deleteItem(i)
            }

            override fun onError(e: Throwable) {
                TODO("Not yet implemented")
            }
        })
        repository.getChoresHistory(object: DisposableSingleObserver<List<ChoresHistory>>() {
            override fun onSuccess(t: List<ChoresHistory>) {
                for (i in t)
                    repository.database.actionDao().deleteItem(i)
            }

            override fun onError(e: Throwable) {
                TODO("Not yet implemented")
            }
        })

        updateValues()
    }

    fun setActualPrice(item:ActionEntity){
        val multiplier:Float = r.nextInt((item.minMultiplier*10000).toInt(), (item.maxMultiplier*10000).toInt()).toFloat()
        Log.i("main", "multiplier $multiplier")
        Log.i("main", "${item.actualPrice}, ${(multiplier/10000)*item.actualPrice}")
        item.oldPrice = item.actualPrice
        item.actualPrice = (multiplier/10000)*item.actualPrice
    }

    fun updateValues(){
        repository.loadValue(ActionEntity(1, ActionType.Metal, "Золото",
            "Золото — элемент 11 группы, шестого периода периодической системы химических элементов, с атомным номером 79. Простое вещество золото — благородный металл жёлтого цвета",
            4000, 1f, 1.03f), null)

        repository.loadValue(ActionEntity(2, ActionType.Metal, "Серебро",
            "Серебро — элемент 11 группы, пятого периода периодической системы химических элементов Д. И. Менделеева, с атомным номером 47. Простое вещество серебро — ковкий, пластичный благородный металл серо-белого цвета. ",
            52, 1f, 1.03f), null)
        repository.loadValue(
            ActionEntity(3, ActionType.Metal, "Платина",
                "Платина — химический элемент 10-й группы, 6-го периода периодической системы химических элементов, с атомным номером 78; блестящий благородный металл серебристо-белого цвета",
                2300, 1f, 1.03f), null)
        repository.loadValue(
            ActionEntity(4, ActionType.Fond, "ВТБ Ликвидность",
                "В состав фонда ВТБ Ликвидность входят ОФЗ, но при этом имеет важную особенность в виде РЕПО сделок (РЕПО с ЦК с КСУ).\n" +
                        "Комиссия фонда: 0.40%, в том числе:\n" +
                        "• УК: 0.21%\n" +
                        "• Спецдеп и иные расходы: 0.19%\n" +
                        "РЕПО - сделка продажи ОФЗ с обязательством обратной покупки через определенный срок по заранее определённой цене, где второй стороной по сделке является Центробанк РФ. По факту же это аналог выдачи обычного займа с залогом в виде ОФЗ. Обычно сделки РЕПО недоступны у большинства российских брокеров, поэтому появление нового фонда уникальная возможность для входа в новый вид активов.",
                122, 0.985f, 1.08f), null)
        repository.loadValue(
            ActionEntity(5, ActionType.Stocks, "Банк ВТБ",
                "ВТБ - второй по величине активов после Сбербанка российский коммерческий банк.\n" +
                        "Основан в 1990 году как Банк Внешней торговли, сокращенно — Внешторгбанк. После преобразования Внешторгбанка в открытое акционерное общество в 1998 практически все акции (96,8 %) кредитной организации принадлежали государству в лице Центробанка, однако через четыре года акции были переданы Федеральному агентству по управлению государственным имуществом.\n" +
                        "С 2006 года Внешторгбанк и большинство одноименных дочерних предприятий носит нынешнее название — ВТБ. В 2007 ВТБ провел IPO. В процессе первичного размещения были размещены акции в количестве 22,5 % от всех акций ВТБ.",
                1, 0.985f, 1.08f), null)
        repository.loadValue(
            ActionEntity(6, ActionType.Fond, "ВТБ Корпоративные облигации",
                "В состав фонда ВТБ – Корпоративные облигации входит 97 выпусков облигаций.\n" +
                        "Комиссия фонда: 0.71%, в том числе:\n" +
                        "• УК: 0.52%\n" +
                        "• Спецдеп и иные расходы: 0.19%\n" +
                        "ВТБ Капитал и ВТБ Капитал Брокер выпустили фонд в 2019 году, создан по российскому праву, торгуется на Московской бирже под тикером VTBB. Фонд повторяет по составу и структуре Индекс корпоративных облигаций Московской Биржи. Выбор наиболее надежных корпоративных облигаций индекса позволяет сформировать портфель с оптимальным соотношением риска и доходности.",
                122, 0.985f, 1.08f), null)
        repository.loadValue(
            ActionEntity(7, ActionType.Fond, "ВТБ Корп облигации США",
                "В состав фонда ВТБ Корп облигации США входят паи международного ETF ISHARES HIGH YIELD CORP BOND.\n" +
                        "Комиссия фонда: 0.71%, в том числе:\n" +
                        "• УК: 0.22%\n" +
                        "• Спецдеп и иные расходы: 0.19%\n" +
                        "• ETF: 0.30%\n" +
                        "В составе ETF более тысячи корпоративных облигаций Американских компаний, состав которых внутри ETF может меняться. Таким образом, фонд VTBH является “фондом фондов”, обеспечивая косвенное владение паями. Таким образом, удержание комиссий происходит два раза, уменьшая доходность конечного инвестора.",
                80, 0.985f, 1.08f), null)
        repository.loadValue(
                ActionEntity(8, ActionType.Stocks, "Alibaba",
                    "Alibaba Group Holding Limited – китайская компания, представляет собой эдакую комбинацию Amazon, eBay и PayPal и на ее долю приходится 80% всей интернет-торговли в КНР. Многомиллиардный бизнес включает в себя торговую площадку Alibaba.com и два шопинг-ресурса: Taobao и Tmall, не считая последних приобретений.\n" +
                            "Taobao, площадка для мелких торговцев, которые платят за рекламу и другие возможности сайта.\n" +
                            " Еще один сайт Alibaba – Tmall -  предназначен для крупных продавцов, здесь свою продукцию реализуют Nike, Apple, Microsoft, UNIQLO, Gap и другие мировые бренды. В отличие от Taobao, чтобы вести торговлю на Tmall, продавцы вносят ежегодную плату, а также отчисляют комиссию за каждую сделку.\n" +
                            "Alibaba также создала свою собственную систему платежей Alipay. Alipay стала настолько популярна в Китае, что, когда компания создала свой денежный фонд, он стал одним из крупнейших в мире всего за 8 месяцев. В сентябре 2014 года корпорация провела успешное IPO.",
                    16299, 0.85f, 1.35f), null)
        repository.loadValue(
        ActionEntity(9, ActionType.Stocks, "Газпром",
            "Газпром – крупнейшая газовая компания в мире, занимающая лидирующие позиции на рынке добычи, транспортировки и реализации газа в России и за рубежом. Основным рынком сбыта компании является РФ. Второе место занимает Европа, на которую приходится около трети общего объема поставок. Компания также занимается добычей нефти и нефтепереработкой. Основной акционер Газпрома - государство, его доля в акционерном капитале холдинга составляет 50,2%. На текущий момент это крупнейшая российская компания по рыночной капитализации.\n" +
                    "Акции Газпрома (GAZP) входят в состав «голубых фишек» российского фондового рынка, являясь одними из наиболее ликвидных бумаг на Московской бирже. Их ежедневный оборот составляет 1/5 от всего объема торгов. Кроме того, бумаги компании получили листинг на Лондонской бирже и торгуются на ней под тиккером OGZD. АДР холдинга также представлены на внебиржевых рынках США, Берлинской и Франкфуртской биржах.",
            366, 0.85f, 1.35f), null)
        repository.loadValue(
            ActionEntity(10, ActionType.Stocks, "АЛРОСА",
                "АЛРОСА – крупнейшая в мире компания по добыче алмазов. Алмазодобытчик делает ставку на рынок развивающихся стран, в частности, Индии и Китая, где ожидается увеличение спроса на ювелирные украшения. Около 70% выручки АЛРОСы приходится именно на поставки камней для ювелирной промышленности.",
                138, 0.85f, 1.35f), null)
        repository.loadValue(
            ActionEntity(11, ActionType.Stocks, "ChemoCentryx, Inc",
                "ChemoCentryx, Inc. - биофармацевтическая компания, которая исследует низкомолекулярные препараты для перорального применения, предназначенные для лечения воспалительных заболеваний.",
                3723, 0.85f, 1.35f), null)
        repository.loadValue(
            ActionEntity(12, ActionType.Stocks, "Сбер Банк",
                "Сбербанк – старейший российский банк. Занимает первое место в России по объему активов – на его долю приходится порядка 30% банковской системы РФ. Услугами банка пользуется более половины населения страны. Филиальная сеть Сбербанка насчитывает 19 тысяч отделений по всей территории России. На международном рынке финансовая организация представлена в 22 странах и насчитывает более 10 млн клиентов за пределами РФ.\n" +
                        "В последнее время Сбербанк активно расширяет свое присутствие на рынке финансовых услуг. В 2012 году кредитная организация приобрела инвестиционную компанию Тройка диалог, которая была преобразована в Sberbank СIB. Это подразделение занимается предоставлением инвестиционных и брокерских услуг.\n" +
                        "В 2013 году  Сбербанк вышел на рынок страхования жизни. Российская Федерация в лице Министерства финансов РФ является владельцем доли 50% плюс одна голосующая акция от уставного капитала Сбербанка (52,32% от общего количества голосующих акций). Миноритарным акционерам принадлежит 50% минус одна голосующая акция Сбербанка в свободном обращении.",
                373, 0.85f, 1.35f), null)
        repository.loadValue(
            ActionEntity(13, ActionType.Stocks, "ПИК",
                "ПАО «Группа Компаний ПИК» занимается строительством объектов жилой недвижимости, продает готовое жилье и осуществляет поддержку своего жилья как самостоятельно, так и с помощью других компаний. В основном группа работает в Москве и Московской области.\n" +
                        "ПИК является самым крупным девелопером в России. За более чем 10 лет деятельности компания построила около 19 миллионов квадратных метров жилой недвижимости, это более 300 тысяч квартир. Группа Компаний ПИК имеет кредитный рейтинг BBB+ от аналитического рейтингового агентства, это самый высокий рейтинг среди девелоперов в России.\n" +
                        "Компания была образована в 1994 году как первая ипотечная компания, в следствии ставшая ПАО «Группа Компаний ПИК». В 2007 году ПИК провел успешное первичное размещение акций, выйдя на международный финансовый рынок.",
                1312, 0.85f, 1.35f), null)
        repository.loadValue(
                ActionEntity(14, ActionType.Stocks, "Vipshop ",
                    "Vipshop — интернет-магазин качественной одежды и аксессуаров в Китае. Особенность магазина — большие сезонные скидки на все товары. Vipshop первым протестировали интернет-распродажи в Китае и показали, что такая модель работает и приносит прибыль.\n" +
                            "Vipshop — самый большой дисконтный интернет-магазин. Компания охватывает 90% рынка распродаж и специальных предложений брендовых товаров. За 2016 год компания заработала \$8,2 млрд.\n" +
                            "Шен и Хонг основали Vipshop в 2008 году, когда осознали, что в Китае нет интернет-площадки, продающей брендовые вещи с большими скидками раз в сезон. Основатели хотели сделать китайский Vente Privee — люксовый интернет-аутлет. Со временем Шен и Хонг сместили фокус на товары среднего сегмента: китайские покупатели не были заинтересованы в дорогих часах, сумках и предметах искусства. ",
                    1136, 0.85f, 1.35f), null)


        repository.loadValue(
        ActionEntity(37, ActionType.Fuch, "фьючерс РТС",
            "",
            10000, 0.5f, 2f), null)

        repository.loadValue(
            ActionEntity(38, ActionType.Fuch, "фьючерс S&P 500 E-mini",
                "",
                10000, 0.5f, 2f), null)

        repository.loadValue(
            ActionEntity(39, ActionType.Fuch, "фьючерс Сырая Нефть",
                "",
                10000, 0.5f, 2f), null)

        repository.loadValue(
            ActionEntity(40, ActionType.Fuch, "фьючерс на доллар",
                "",
                10000, 0.5f, 2f), null)

        /*
        repository.loadValue(
        ActionEntity(3, ActionType.Metal, "",
            "",
            , f, f), null)



         */



    }


    companion object{
        fun getIteration(iteration:Int):String{
            val year = ((iteration-1)/4) + 2021
            val month = when ((iteration-1)%4){
                0 -> "Январь"
                1 -> "Апрель"
                2 -> "Июль"
                else -> "Октябрь"
            }
            return "$month, $year"
        }
    }
}