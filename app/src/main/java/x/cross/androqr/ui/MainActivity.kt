package x.cross.androqr.ui

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.budiyev.android.codescanner.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import x.cross.androqr.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    companion object{
        private const val RQ_CODE_CAMERA = 1
    }

    private lateinit var view: ActivityMainBinding
    private lateinit var codeScanner: CodeScanner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        setContentView(view.root)

        supportActionBar?.hide()

        openQRScanner()
    }

    @AfterPermissionGranted(RQ_CODE_CAMERA)
    private fun openQRScanner(){
        //Есть ли разрешение
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            codeScanner = CodeScanner(this, view.scannerView)

            // Parameters (default values)
            codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
            codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
            // ex. listOf(BarcodeFormat.QR_CODE)
            codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
            codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
            codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
            codeScanner.isFlashEnabled = false // Whether to enable flash or not

            // Код отсканирован
            codeScanner.decodeCallback = DecodeCallback {
                runOnUiThread {
                    Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
                }
            }

            // Ошибка инициализации камеры
            codeScanner.errorCallback = ErrorCallback {
                runOnUiThread {
                    Toast.makeText(this, "Camera initialization error: ${it.message}",
                            Toast.LENGTH_LONG).show()
                }
            }

            view.scannerView.setOnClickListener {
                codeScanner.startPreview()
            }
        } else { //Нет разрешения на камеру
            EasyPermissions.requestPermissions(this, "Права суука дал живо!",
                    RQ_CODE_CAMERA, Manifest.permission.CAMERA)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}