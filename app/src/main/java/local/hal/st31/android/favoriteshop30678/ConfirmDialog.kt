package local.hal.st31.android.favoriteshop30678

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ConfirmDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(R.string.dlg_full_msg)
        builder.setMessage(R.string.dlg_msg)
        builder.setPositiveButton(R.string.dlg_bt_ok, DialogButtonClickListener())
        builder.setNegativeButton(R.string.dlg_bt_ng, DialogButtonClickListener())
        return builder.create()
    }

    private inner class DialogButtonClickListener : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface, which: Int) {
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    val activity = requireActivity()
                    if (activity is ShopEditActivity) {
                        activity.deleteShopAfterConfirm()
                    } else {
                        Toast.makeText(activity, "エラーが発生しました。", Toast.LENGTH_SHORT).show()
                    }
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                    dialog.cancel()
                }
            }
        }
    }
}