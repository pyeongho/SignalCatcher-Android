package com.example.signalcatcher

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import java.io.File
import java.io.FileReader
import java.lang.Exception
import java.util.*
import java.util.Map
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {

    private val REQUEST_READ_EXTERNAL_STORAGE = 1000
    private val EXTERNAL_STORAGE_PATH = Environment.getExternalStorageDirectory().toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var list = mutableListOf<String>()
        var arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        listView.adapter = arrayAdapter

        btn_add.setOnClickListener {
            // 사용자 권한 체크
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 허용되지 않았다면 다시 확인
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    alert("카카오톡 대화를 가져오려면 외부 저장소 권한이 필수로 필요합니다. 동의하시겠습니까?", "권한 승인"){
                        yesButton {
                            // 권한 허용.
                            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_EXTERNAL_STORAGE)
                        }
                        noButton {
                            // 권한 불허용.
                        }
                    }.show()
                } else {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_EXTERNAL_STORAGE)
                }
            }else{
                /* 프로그레스 다이얼로그 (보류)
                val progressDialog = indeterminateProgressDialog("Searching...", "탐색 중")
                progressDialog.setCancelable(false)
                progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal)
                progressDialog.show()
                progressDialog.dismiss()
                */

                val talkFiles = getTalkFiles()
                var fileNames = Array(talkFiles.size){""}
                for (i in talkFiles.indices) {
                    fileNames[i] = talkFiles[i].fileName
                }

                var listDialog = AlertDialog.Builder(this)
                listDialog.setTitle("카카오톡 대화 목록")
                listDialog.setNegativeButton("취소", null)
                var listener = object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        selectTalk(talkFiles[which].filePath, list, arrayAdapter)
                    }
                }
                listDialog.setItems(fileNames, listener)
                listDialog.show()
            }
        }
        btn_option.setOnClickListener {
            toast("설정")
        }
        list_empty.setOnClickListener {
            toast("우측 상단의 버튼으로 대화를 추가해주세요.")
        }
    }

    // 카카오톡 내보내기 txt 목록 가져오기
    fun getTalkFiles() : MutableList<TalkFile> {
        val talkFiles = mutableListOf<TalkFile>()
        var fileName : String
        var fileContent : String
        var filePath : String
        var fileDate : String
        File(EXTERNAL_STORAGE_PATH).walk().forEach {
            if (it.toString().contains("KakaoTalk_Chats") && it.extension.contentEquals("txt")) {
                filePath = it.absolutePath
                fileContent = it.readText()
                fileName = fileContent.substring(1, fileContent.indexOf(" 님과 카카오톡 대화"))
                fileDate = filePath.substring(filePath.indexOf("/KakaoTalk_Chats_") + 17, filePath.indexOf("/KakaoTalkChats.txt"))
                fileDate = fileDate.replace("_", " ")
                fileDate = fileDate.replace(".", ":")
                fileName = """${fileName}님과의 대화
                             |${fileDate}""".trimMargin()

                talkFiles.add(TalkFile(fileName, filePath))
                //Log.d("찾은 파일 URI", it.toURI().toString())
                //Log.d("찾은 파일 readText", it.readText())
            }
        }
        return talkFiles
    }

    // 선택한 카카오톡 대화 출력
    fun selectTalk(filePath : String, list: MutableList<String>, arrayAdapter: ArrayAdapter<String>) {
        if(list.size > 0) {
            list.clear()
        }
        try {
            val read = FileReader(filePath)
            list_empty.visibility = View.GONE
            val cal = Calendar.getInstance()
            var now = "${cal.get(Calendar.YEAR)}.${cal.get(Calendar.MONTH)+1}.${cal.get(Calendar.DATE)} "
            now += "${cal.get(Calendar.HOUR_OF_DAY)}:${cal.get(Calendar.MINUTE)}:${cal.get(Calendar.SECOND)}"
            list.add(read.readText())
            arrayAdapter.notifyDataSetChanged()
        } catch (e : Exception) {
            Log.e("에러", e.message)
        }
    }

    data class TalkFile (val fileName : String, val filePath : String)
}