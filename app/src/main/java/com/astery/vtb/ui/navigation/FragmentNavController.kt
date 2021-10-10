package com.astery.vtb.ui.navigation

import android.os.Bundle
import com.astery.copying.forms_adapter.navigation.NavigationTransition
import com.astery.copying.forms_adapter.navigation.SharedAxisTransition
import com.astery.vtb.ui.fragments.*
import com.google.android.material.transition.MaterialSharedAxis

/** navigation controller
 * fragments can start another fragment not with calling this fragment itself, but starting action.
 */
enum class FragmentNavController {
    SELECT_LEVEL {
        override val thisFragment: XFragment
            get() = SelectDifficultyFragment()
        override val parent: FragmentNavController?
            get() = null
        override val transition: NavigationTransition
            get() = SharedAxisTransition().setAxis(MaterialSharedAxis.Z).setFirstParent(true)
    },
    ACTIONS {
        override val thisFragment: XFragment
            get() = ActionsFragment()
        override val parent: FragmentNavController?
            get() = null
        override val transition: NavigationTransition
            get() = SharedAxisTransition().setAxis(MaterialSharedAxis.Z).setFirstParent(true)
    },
    SELECTED_ACTIONS {
        override val thisFragment: XFragment
            get() = SelectedActionsFragment()
        override val parent: FragmentNavController?
            get() = null
        override val transition: NavigationTransition
            get() = SharedAxisTransition().setAxis(MaterialSharedAxis.Z).setFirstParent(true)
    },
    HISTORY {
        override val thisFragment: XFragment
            get() = HistoryFragment()
        override val parent: FragmentNavController?
            get() = null
        override val transition: NavigationTransition
            get() = SharedAxisTransition().setAxis(MaterialSharedAxis.Z).setFirstParent(true)
    };

    /** transition settings. It may be useful if it's required to get action from different places */
    protected var transitionBundle: Bundle? = null

    constructor(transitionBundle: Bundle) {
        this.transitionBundle = transitionBundle
    }

    constructor()

    /** get XFragment object for this action  */
    abstract val thisFragment: XFragment?

    /** get XFragment when back pressed  */
    abstract val parent: FragmentNavController?

    /** get transition to transform to this action */
    abstract val transition: NavigationTransition?

    /** throws runtime exception if the action started from wrong fragment. This method prevent you from creating new communication
     * with two fragment without declaring it here  */
    fun checkFriends(controller: FragmentNavController) {
        throw RuntimeException(name + " started from wrong fragment " + controller.name)
    }
}