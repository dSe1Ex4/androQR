package x.cross.androqr.ui

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.budiyev.android.codescanner.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import x.cross.androqr.databinding.ActivityMainBinding
import x.cross.androqr.R


class MainActivity : BaseActivity() {
    companion object{
        private const val RQ_CODE_CAMERA = 1
    }


    private lateinit var view: ActivityMainBinding
    private var codeScanner: CodeScanner? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        setContentView(view.root)

        supportActionBar?.hide()

        openQRScanner()
    }

    private fun openQRScanner(){
        //Есть ли разрешение
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            initQRScanner()
        } else { //Нет разрешения на камеру
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.alert_info_title))
                .setMessage(R.string.alert_camera_permission_denied)
                .setPositiveButton(getString(R.string.alert_give_text)) { _: DialogInterface, _: Int -> reqCameraPermission()}
                .show()
        }
    }

    @AfterPermissionGranted(RQ_CODE_CAMERA)
    private fun initQRScanner(){
        codeScanner = CodeScanner(this, view.scannerView)
        // Parameters (default values)

        codeScanner?.let { cs ->
            cs.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
            cs.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
            // ex. listOf(BarcodeFormat.QR_CODE)
            cs.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
            cs.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
            cs.isAutoFocusEnabled = true // Whether to enable auto focus or not
            cs.isFlashEnabled = false // Whether to enable flash or not

            // Код отсканирован
            cs.decodeCallback = DecodeCallback {
                runOnUiThread {
                    Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
                }
                startActivity(Intent(this, DetailActivity::class.java)
                        .putExtra("UUID", it.text)
                )
            }

            // Ошибка инициализации камеры
            cs.errorCallback = ErrorCallback {
                AlertDialog.Builder(this)
                        .setTitle(getString(R.string.alert_error_title))
                        .setMessage(getString(R.string.alert_camera_error) + {it.message})
                        .setPositiveButton(getString(R.string.alert_camera_ok)) { _: DialogInterface, _: Int -> }
                        .show()
            }

            view.scannerView.setOnClickListener { cs.startPreview() }
        }
    }

    private fun reqCameraPermission(){
        EasyPermissions.requestPermissions(this,
                getString(R.string.alert_camera_permission_denied), RQ_CODE_CAMERA,
                Manifest.permission.CAMERA)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    override fun onResume() {
        super.onResume()
        codeScanner?.startPreview()
    }

    override fun onPause() {
        codeScanner?.releaseResources()
        super.onPause()
    }
}