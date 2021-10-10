package com.astery.copying.forms_adapter.navigation

abstract class NavigationTransition {
    /** first = true will be applied for current fragment  */
    var isFirst = true
        protected set

    fun setFirst(isFirst: Boolean): NavigationTransition {
        this.isFirst = isFirst
        return this
    }

    abstract fun reverseCopy(): NavigationTransition?
}