package com.olexii8bit.notesapp.presentation.addEditCategory

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.LifecycleOwner
import com.olexii8bit.notesapp.R
import com.olexii8bit.notesapp.data.repository.model.Category

class AddEditCategoryDialogFragment : DialogFragment() {

    companion object {
        const val NEW_CATEGORY_RESULT_KEY = "NEW_CATEGORY_RESULT_KEY"
        const val UPDATE_CATEGORY_RESULT_KEY = "UPDATE_CATEGORY_RESULT_KEY"
        private const val CATEGORY_ARG = "ARG_NOTE"
        private val TAG = AddEditCategoryDialogFragment::class.java.simpleName

        fun show(manager: FragmentManager, category: Category?) {
            val fragment = AddEditCategoryDialogFragment()
            fragment.arguments = bundleOf(CATEGORY_ARG to category)
            fragment.show(manager, TAG)
        }

        fun listenResult(
            key: String,
            manager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            listener: (Category) -> Unit,
        ) {
            manager.setFragmentResultListener(
                key,
                lifecycleOwner
            ) { _, result ->
                @Suppress("DEPRECATION")
                listener.invoke((result.getParcelable(CATEGORY_ARG) as? Category)!!)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        @Suppress("DEPRECATION")
        val currentCategory =
            (this.requireArguments().getParcelable(CATEGORY_ARG) as? Category)
        val editText = EditText(this.requireContext())

        return if (currentCategory == null) {
            AlertDialog.Builder(this.requireContext())
                .setTitle(R.string.categoryDialogTitle)
                .setPositiveButton(R.string.categoryDialogConfirm) { _: DialogInterface, _: Int ->
                    val newCategory = Category(editText.text.toString())
                    setFragmentResult(
                        NEW_CATEGORY_RESULT_KEY,
                        bundleOf(CATEGORY_ARG to newCategory)
                    )
                }
                .setView(editText)
                .create()
        } else {
            editText.setText(currentCategory.name)

            AlertDialog.Builder(this.requireContext())
                .setTitle(R.string.categoryDialogTitle)
                .setPositiveButton(R.string.categoryDialogConfirm) { _: DialogInterface, _: Int ->
                    val updateCategory = currentCategory.copy(name = editText.text.toString())
                    setFragmentResult(
                        UPDATE_CATEGORY_RESULT_KEY,
                        bundleOf(CATEGORY_ARG to updateCategory)
                    )
                }
                .setView(editText)
                .create()
        }


    }
}