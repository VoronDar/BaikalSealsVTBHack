package com.astery.copying.forms_adapter.navigation

class SharedAxisTransition : NavigationTransition() {
    var axis = 0
        private set

    /** firstParent = true for cases when current fragment - parent  */
    var firstParent = false
        private set

    fun setAxis(axis: Int): SharedAxisTransition {
        this.axis = axis
        return this
    }

    fun setFirstParent(isParent: Boolean): SharedAxisTransition {
        firstParent = isParent
        return this
    }

    override fun reverseCopy(): NavigationTransition {
        return SharedAxisTransition().setAxis(axis)
    }
}