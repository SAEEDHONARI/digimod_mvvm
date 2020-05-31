package com.example.mvvm_digimode.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider

import com.bumptech.glide.Glide
import com.example.mvvm_digimode.R
import com.example.mvvm_digimode.Repository.App
import com.example.mvvm_digimode.Repository.Factory
import com.example.mvvm_digimode.ViewModel.ViewModel_Openion
import dmax.dialog.SpotsDialog
import io.paperdb.Paper

@Suppress("DEPRECATION")
class SendMsgActivity : AppCompatActivity() {


    lateinit var Img_Angry: ImageView
    lateinit var Img_normal: ImageView
    lateinit var Img_funny: ImageView
    lateinit var txt_status : TextView
    lateinit var input_title: EditText
    lateinit var input_context: EditText
    lateinit var btn_submit_msg: Button
    var id_product:String=""
    var str_title_msg:String=""
    var str_context_msg:String=""
    var strStatus:String=""
    var Username:String=""

    var txt_url_Img:String=""
    var txt_desc:String=""
    lateinit var Img_toolbar: ImageView
    lateinit var Txt_toolbar: TextView
    lateinit var Img_back: ImageView

    lateinit var viewmodelDetail:ViewModel_Openion
    lateinit var wait: android.app.AlertDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_msg)

        initViews()

        setFace()

        providValue()

        wait = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(R.string.msg_SendOpenion)
            .setCancelable(false)
            .build()

        viewmodelDetail=
            ViewModelProvider(this, Factory(App())).get(ViewModel_Openion::class.java)



    }

    private fun providValue() {

        Username = Paper.book().read<String>("Username")
        val extras = intent.extras

        if (extras != null) {
            id_product = extras.getString("id_product").toString()
            txt_url_Img=extras.getString("Img_product").toString()
            txt_desc=extras.getString("dec_product").toString()
            Img_toolbar=findViewById(R.id.Img_product_msg)
            Txt_toolbar=findViewById(R.id.tv_desc_toolbar_msg)

            Txt_toolbar.setText(txt_desc);

            Glide
                .with(this)
                .load(txt_url_Img)
                .placeholder(R.drawable.logo_dm_gray_marker)
                .into(Img_toolbar)
        }
    }


    private fun initViews() {


        Img_back=findViewById(R.id.Img_back_msg)

        Img_back.setOnClickListener(View.OnClickListener {  finish()  })
        Img_Angry=findViewById(R.id.Img_angry)
        Img_funny=findViewById(R.id.Img_funny)
        Img_normal=findViewById(R.id.Img_normal)
        txt_status=findViewById(R.id.status_msg)

        input_title=findViewById(R.id.input_title_msg)
        input_context=findViewById(R.id.input_context_msg)
        btn_submit_msg=findViewById(R.id.btn_submit_msg)



        btn_submit_msg.setOnClickListener(View.OnClickListener {
            str_title_msg=input_title.text.toString()
            str_context_msg=input_context.text.toString()
            strStatus=txt_status.text.toString()

            if (strStatus.isEmpty()){
                txt_status.setError("لطفا میزان رضایت خود را مشخص نمایید.")
                txt_status.requestFocus()
            }else if (str_title_msg.isEmpty()){
                input_title.setError("عنوان را وارد نمایید.")
                input_title.requestFocus()
            }else if(str_context_msg.isEmpty()){
                input_context.setError("متن دیدگاه خود را وارد نمایید.")
                input_context.requestFocus()
            }else {
                wait.show()
             //   viewmodelDetail.send_openionProduct(Username,id_product,strStatus,str_title_msg,str_context_msg)
             //   viewmodelDetail.str_sendMsg.observe(this, androidx.lifecycle.Observer {

              //  })
           //     sendOpenionToServer(Username, id_product,strStatus,str_title_msg,str_context_msg)
            }
        })
    }
/*
    private fun sendOpenionToServer(Username: String, idProduct: String, strStatus: String, strTitleMsg: String, strContextMsg: String) {


        val queue = Volley.newRequestQueue(this)
        val url = "http://honarijamshid.cloudsite.ir/dgmod/set_openion.php"


        var postRequest=object : StringRequest(
            Request.Method.POST,url,
            Response.Listener<String> { response ->

                Log.e("Response11:",response)

                if (!response.isEmpty()) {
                    wait.dismiss()
                    Toast.makeText(
                        this,
                        "ثبت اطلاعات با موفقیت انجام شد",
                        Toast.LENGTH_LONG

                    ).show()

                    finish()


                } else {

                    Toast.makeText(
                        this,
                        "خطا در ثبت اطلاعات",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }


            },
            Response.ErrorListener {
                Toast.makeText(
                    this,
                    "خطا در برقراری ارتباط با سرور!",
                    Toast.LENGTH_LONG
                ).show()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["username"] = Username
                params["title_msg"] = strTitleMsg
                params["context_msg"] = strContextMsg
                params["id_product"] = idProduct
                params["status"] = strStatus

                return params
            }
        }
        queue.add(postRequest)


    }
*/

    private fun setFace() {
        Img_Angry.setOnClickListener(View.OnClickListener {
            Img_normal.setImageResource(R.drawable.normal_gray)
            Img_funny.setImageResource(R.drawable.funny_gray)
            Img_Angry.setImageResource(R.drawable.ungry_color)
            txt_status.setText("بد")
            txt_status.setTextColor(resources.getColor(R.color.red600))
        })

        Img_normal.setOnClickListener(View.OnClickListener {
            Img_normal.setImageResource(R.drawable.normal)
            Img_funny.setImageResource(R.drawable.funny_gray)
            Img_Angry.setImageResource(R.drawable.ungry_gray)
            txt_status.setText("معمولی")
            txt_status.setTextColor(resources.getColor(R.color.orange300))

        })
        Img_funny.setOnClickListener(View.OnClickListener {
            Img_normal.setImageResource(R.drawable.normal_gray)
            Img_funny.setImageResource(R.drawable.funny_color)
            Img_Angry.setImageResource(R.drawable.ungry_gray)
            txt_status.setText("خوب")
            txt_status.setTextColor(resources.getColor(R.color.greenface))

        })
    }



}