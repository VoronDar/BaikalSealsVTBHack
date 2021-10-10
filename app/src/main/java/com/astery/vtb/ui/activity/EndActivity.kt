package com.astery.vtb.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.astery.vtb.MainViewModel
import com.astery.vtb.application.App
import com.astery.vtb.databinding.ActivityEndBinding

class EndActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEndBinding

    private val viewModel:MainViewModel by viewModels()


    private var state = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEndBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.message.text = "Поздравляем!\n" +
                "За три года инвестирования, вы:"
        binding.description.text = "Совершили 7 сделок, получив 21323.90 р. прибыли\n\nДостигли высокого (>70%) уровня счастья\n\nСоздали свою семью"

        state = 1

        binding.button.setOnClickListener{
            if (state == 0) {
                viewModel.setup(applicationContext, (application as App).container.repository)
                viewModel.resetEverything()
                state = 1
                binding.message.text =
                    "Это потрясающий результат, которого вы можете достичь в реальной жизни!"
                binding.description.text = "Попробуйте приложение\n" +
                        " “ВТБ: Мои инвестиции”, и получите подарочный набор ценных бумаг, общей стоимостью до 20000 Рублей."
                binding.button.text = "Установить приложение"
            } else{
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=ru.vtb.invest"))
                startActivity(browserIntent)
            }
        }

        binding.share.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://cdn1.ozone.ru/s3/multimedia-k/6069506588.jpg"))
            startActivity(browserIntent)
        }
    }
}