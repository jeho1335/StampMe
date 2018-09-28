package srjhlab.com.myownbarcode.Dialog

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.jhmk.stampme.Model.MyStamps
import com.jhmk.stampme.Model.User
import com.jhmk.stampme.Module.Dialog.Barcode
import com.jhmk.stampme.Module.Dialog.BarcodeDialogPresenter
import com.jhmk.stampme.R
import kotlinx.android.synthetic.main.layout_barcode_dialog.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class CommonDialog : android.support.v4.app.DialogFragment(), Barcode.view {
    private val TAG = this.javaClass.simpleName
    private lateinit var mPresenter : BarcodeDialogPresenter
    private lateinit var mItems: MutableList<MyStamps>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.d(TAG, "##### onCreateView #####")
        dialog.window.attributes.windowAnimations = R.style.SelectDialogAnimation
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        dialog.setCanceledOnTouchOutside(true)
        return inflater.inflate(R.layout.layout_barcode_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "##### onActivityCreated #####")
        super.onActivityCreated(savedInstanceState)
        mPresenter = BarcodeDialogPresenter(this)
        initializeUi()
    }

    fun setITems(items: MutableList<MyStamps>): CommonDialog {
        Log.d(TAG, "##### setUser #####")
        this.mItems = items
        return this
    }

    private fun initializeUi() {
        Log.d(TAG, "##### initializeUi #####")
    }

    override fun onResultMakeBarcode(user: User, bitmap: Bitmap?) {
        Log.d(TAG, "##### onResultMakeBarcode #####")
        img_barcode.setImageBitmap(bitmap)
    }
}