package com.example.mvvm_digimode.view
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.daimajia.slider.library.SliderLayout
import com.example.mvvm_digimode.R
import com.example.mvvm_digimode.Repository.App
import com.example.mvvm_digimode.Repository.Factory
import com.example.mvvm_digimode.ViewModel.ViewModel_Openion
import com.example.mvvm_digimode.databinding.ActivityDetailBinding
import com.example.mvvm_digimode.model.Openion_Model
import com.example.mvvm_digimode.model.Product_Model
import com.example.mvvm_digimode.view.DetailSlider.SliderAdapterDetail
import com.example.mvvm_digimode.view.DetailSlider.SliderDetailModel
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_detail.*
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
import java.util.concurrent.atomic.AtomicReference

class DetailActivity : AppCompatActivity(), ObservableScrollView.OnScrollChangedListener {

    lateinit var ReciveItemInfo: Product_Model
    lateinit var mScrollView: ObservableScrollView
    lateinit var imgContainer: View

    lateinit var txt_count_openion: TextView
    lateinit var btn_add_openion: Button
    lateinit var add_to_cart: TextView


    lateinit var imageView: ImageView
    lateinit var Img_favorite: ImageView
    lateinit var txt_title: TextView
    lateinit var txt_price: TextView
    lateinit var layoutDiscount: LinearLayout
    lateinit var txt_discount: TextView
    lateinit var title_time: TextView
    lateinit var send_time: TextView
    lateinit var txt_persent_discount: TextView
    lateinit var txt_desc: TextView
    lateinit  var txt_barcode: TextView
    lateinit var txt_cate: TextView
    lateinit var txt_countInCart: TextView
    lateinit var img_slider: SliderLayout
    lateinit var Img_back : ImageView
    lateinit var Img_cart : ImageView
    var firstClick = true
    var Img2 = ""
    var Img3 = ""
    var Img4 = ""
    var ID = ""

    lateinit var viewmodelDetail:ViewModel_Openion
    var Cate1 = ""
    var Cate2 = ""

    lateinit var ListOfNumberCart: AtomicReference<List<String>>
    lateinit var dl: DrawerLayout

    lateinit var sliderView: SliderView
    lateinit var adapterSlider: SliderAdapterDetail

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            backGroundColor()
        }
        Paper.init(this)
        setBinding()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initViews()
        }

         viewmodelDetail=
            ViewModelProvider(this, Factory(App())).get(ViewModel_Openion::class.java)

        viewmodelDetail.get_Openion_product(ID)
        viewmodelDetail.mutable_Openion.observe(this, androidx.lifecycle.Observer {
            setupRecyclerViewOpenion(it)
        })

        }


    private fun setupRecyclerViewOpenion(list: MutableList<Openion_Model>) {
       recycler_openion.also {
            it.layoutManager=LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

                val adapter=AdapterRecyclerOpenion(list)
                it.adapter=adapter

            }

    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews() {
        // Init your layout and set your listener
        mScrollView = findViewById(R.id.scroll_view) as ObservableScrollView
        mScrollView.setOnScrollChangedListener(this)
        // Store the reference of your image container
        imgContainer = findViewById(R.id.img_container)
        // Store the reference of your image
        Img_back = findViewById(R.id.Img_back_detail)
        Img_back.setOnClickListener(View.OnClickListener { v: View? -> finish() })
        txt_count_openion = findViewById(R.id.count_openion)
        txt_count_openion.setText("بدون نظر")
        btn_add_openion = findViewById(R.id.btn_add_openion)
        add_to_cart = findViewById(R.id.txt_add_cart)
        txt_countInCart = findViewById(R.id.tv_count_detail_cart)
        title_time = findViewById(R.id.txt_time_send_product)
        send_time = findViewById(R.id.txt_time_send_product)
        txt_title = findViewById(R.id.txt_title_detail_product)
        txt_desc = findViewById(R.id.txt_description_detail_product)
        txt_barcode = findViewById(R.id.txt_barcode_detail_product)
        txt_cate = findViewById(R.id.txt_cate_detail_product)
        val extras = intent.extras

        if (extras != null) {
            add_to_cart.setOnClickListener(View.OnClickListener { v: View? ->
                if (!Paper.book("CartID").contains(ID)) {
                    Paper.book("CartID").write(ID, ID)
                    add_to_cart.setText("موجود در سبد خرید شما")
                    if (txt_countInCart.visibility == View.GONE) {
                        txt_countInCart.visibility = View.VISIBLE
                    }
                }
                ListOfNumberCart.set(Paper.book("CartID").allKeys)
                txt_countInCart.text = ListOfNumberCart.get().size.toString()
            })

            Paper.book().write("Username","saeed")

            btn_add_openion.setOnClickListener { v: View? ->
                if (Paper.book().contains("Username")) {
                    val intent = Intent(
                        this,
                        SendMsgActivity::class.java
                    )
                    intent.putExtra("id_product", ID)
                    intent.putExtra("Img_product", Img2)
                    intent.putExtra("dec_product", extras.getString("des"))
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        "ابتدا باید وارد حساب کاربری شوید",
                        Toast.LENGTH_LONG
                    ).show()
                //    startActivity(Intent(this, LoginActivity2::class.java))
                }
            }
            txt_title.text = extras.getString("title")
            txt_desc.text = extras.getString("des")
            /////////////////////////
            val decimalFormat = DecimalFormat("0,000")
            decimalFormat.roundingMode = RoundingMode.CEILING
            val price = extras.getString("price")
            val Intprice = price!!.trim { it <= ' ' }.toInt()
            val DoublePrice = java.lang.Double.valueOf(price)
            val PriceSize = "$price تومان"
            val discount = extras.getInt("discount")
            var productDiscountPrice = ""
            txt_price = findViewById(R.id.txt_price_detail_product)
            txt_discount = findViewById(R.id.txt_price_product_detail)
            txt_persent_discount = findViewById(R.id.txt_persent_discount_detail)
            layoutDiscount = findViewById(R.id.layout_discount)
            if (discount != 0) {
                val b = decimalFormat.format(DoublePrice)
                val spannableString =
                    SpannableString("$b تومان ")
                spannableString.setSpan(
                    StrikethroughSpan(),
                    0,
                    PriceSize.length - 1,
                    Spanned.SPAN_MARK_MARK
                )
                layoutDiscount.visibility = View.VISIBLE
                val DoubleDiscount = discount.toDouble()

                // productDiscountPrice = (price - (price * (persent_discount.toDouble() / 100))).toString()
                productDiscountPrice = (Intprice - Intprice * (DoubleDiscount / 100)).toString()
                val discountDouble = java.lang.Double.valueOf(productDiscountPrice)
                val a = decimalFormat.format(discountDouble)
                // String a = decimalFormat.format((discountDouble).toInt())
                txt_discount.text = spannableString
                txt_price.text = "$a تومان"
                txt_persent_discount.text = "% $discount"
            } else {
                layoutDiscount.visibility = View.GONE
                txt_price.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.pricegreen
                    )
                )
                val priceDontOff = decimalFormat.format(Intprice.toLong())
                txt_price.text = "$priceDontOff تومان"
            }


            /////////////////////////
            txt_barcode.text = extras.getString("barcode")
            Img2 = extras.getString("pic2")!!
            Img3 = extras.getString("pic3")!!
            Img4 = extras.getString("pic4")!!

            //set category to "main category ,Sub Category "
            val cate = extras.getString("category")
            val s = cate!!.split(",").toTypedArray()
            if (s[0] == "1") {
                Cate1 = "لباس و اکسسوری آقایان"
                if (s[1] == "1") {
                    Cate2 = "کت و شلوار"
                } else if (s[1] == "2") {
                    Cate2 = "ست اسپورت مردانه"
                } else if (s[1] == "3") {
                    Cate2 = "تی شرت"
                } else if (s[1] == "4") {
                    Cate2 = "پیراهن"
                } else if (s[1] == "5") {
                    Cate2 = "شلوار"
                } else if (s[1] == "6") {
                    Cate2 = "سویشرت و هودی"
                } else if (s[1] == "7") {
                    Cate2 = "ژاکت و پلیور"
                } else if (s[1] == "8") {
                    Cate2 = "لباس زیر"
                } else if (s[1] == "9") {
                    Cate2 = "جوراب"
                } else if (s[1] == "10") {
                    Cate2 = "جلیقه و کت"
                } else if (s[1] == "11") {
                    Cate2 = "اکسسوری آقایان"
                } else if (s[1] == "12") {
                    Cate2 = "خواب و استراحت"
                }
            } else if (s[0] == "2") {
                Cate1 = "لباس و اکسسوری بانوان"
                if (s[1] == "1") {
                    Cate2 = "مانتو و رویه"
                } else if (s[1] == "2") {
                    Cate2 = "تاپ ، تی شرت و تونیک"
                } else if (s[1] == "3") {
                    Cate2 = "لباس مجلسی"
                } else if (s[1] == "4") {
                    Cate2 = "شال و روسری"
                } else if (s[1] == "5") {
                    Cate2 = "سرهمی و سارافون"
                } else if (s[1] == "6") {
                    Cate2 = "کت و جلیقه"
                } else if (s[1] == "7") {
                    Cate2 = "پایین تنه"
                } else if (s[1] == "8") {
                    Cate2 = "عروسی"
                } else if (s[1] == "9") {
                    Cate2 = "پیراهن"
                } else if (s[1] == "10") {
                    Cate2 = "ژاکت و پلیور"
                } else if (s[1] == "11") {
                    Cate2 = "سویشرت و هودی"
                } else if (s[1] == "12") {
                    Cate2 = "بلوز و شومیز"
                } else if (s[1] == "7") {
                    Cate2 = "خواب و استراحت"
                } else if (s[1] == "8") {
                    Cate2 = "لباس زیر"
                } else if (s[1] == "9") {
                    Cate2 = "جوراب"
                } else if (s[1] == "10") {
                    Cate2 = "جوراب شلواری"
                } else if (s[1] == "11") {
                    Cate2 = "چادر و پوشش اسلامی"
                } else if (s[1] == "12") {
                    Cate2 = "اکسسوری بانوان"
                }
            } else if (s[0] == "3") {
                Cate1 = "لباس و اکسسوری کودکان"
                if (s[1] == "1") {
                    Cate2 = "لباس کودک دخترانه"
                } else if (s[1] == "2") {
                    Cate2 = "لباس کودک پسرانه"
                } else if (s[1] == "3") {
                    Cate2 = "ست های خانودادگی"
                } else if (s[1] == "4") {
                    Cate2 = "لباس فصل پسرانه"
                } else if (s[1] == "5") {
                    Cate2 = "لباس فصل دخترانه"
                }
            } else if (s[0] == "4") {
                Cate1 = "کیف و کفش مردانه"
                if (s[1] == "1") {
                    Cate2 = "کفش مردانه"
                } else if (s[1] == "2") {
                    Cate2 = "کبف مردانه"
                }
            } else if (s[0] == "5") {
                Cate1 = "کیف و کفش زنانه"
                if (s[1] == "1") {
                    Cate2 = "کفش زنانه"
                } else if (s[1] == "2") {
                    Cate2 = "کبف زنانه"
                }
            } else if (s[0] == "6") {
                Cate1 = "کیف و کفش کودکان"
                if (s[1] == "1") {
                    Cate2 = "کفش کودکان"
                } else if (s[1] == "2") {
                    Cate2 = "کیف و کوله کودکان"
                }
            }
            Img_cart = findViewById(R.id.Img_detail_cart)
            Img_cart.setOnClickListener(View.OnClickListener { v: View? ->
               /*
                startActivity(
                    Intent(
                        this,
                        CartActivity::class.java
                    )
                )
               */
            })
            txt_cate.text = "$Cate1 / $Cate2"
            //End Category

            //Favorite
            Img_favorite = findViewById(R.id.Img_detail_favorite)
            if (Paper.book("Favorite").contains(ID)) {
                Img_favorite.setImageResource(R.drawable.ic_favorite_red_24dp)
               firstClick = false
            }
            Img_favorite.setOnClickListener {
                if (firstClick == true) {
                    Img_favorite.setImageResource(R.drawable.ic_favorite_red_24dp)
                    Paper.book("Favorite").write(ID, ID)
                    firstClick = false
                } else {
                    Img_favorite.setImageResource(R.drawable.ic_favorite_white_24dp)
                    Paper.book("Favorite").delete(ID)
                    firstClick = true
                }
            }
            //EndFavotite

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                initSlider()
            }
        }
    }


    private fun setBinding() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val intentPost = intent
        val bind: ActivityDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_detail)

        ID=intentPost.getStringExtra("id")
        ReciveItemInfo = Product_Model(
            intentPost.getStringExtra("barcode")
            , intentPost.getStringExtra("category")
            , intentPost.getStringExtra("des")
            , intentPost.getStringExtra("id")
            , intentPost.getStringExtra("pic1")
            , intentPost.getStringExtra("pic2")
            , intentPost.getStringExtra("pic3")
            ,intentPost.getStringExtra("pic4")
            , intentPost.getIntExtra("discount",0)
            , intentPost.getStringExtra("price")
            ,  intentPost.getStringExtra("title"))

        bind.data=ReciveItemInfo

    }

    override fun onScrollChanged(deltaX: Int, deltaY: Int) {

        // Get scroll view screen bound

        // Get scroll view screen bound
        val scrollBounds = Rect()
        mScrollView.getHitRect(scrollBounds)

        // Check if image container is visible in the screen
        // so to apply the translation only when the container is visible to the user

        // Check if image container is visible in the screen
        // so to apply the translation only when the container is visible to the user
        if (imgContainer.getLocalVisibleRect(scrollBounds)) {
            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)
            // Get screen density
            val density = resources.displayMetrics.density

            // Get screen height in pixels
            val dpHeight = outMetrics.heightPixels / density
            val screen_height_pixels = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpHeight,
                resources.displayMetrics
            ).toInt()
            val half_screen_height = screen_height_pixels / 4

            // Get image container height in pixels
            val container_height_pixels = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                200f,
                resources.displayMetrics
            ).toInt()

            // Get the location that consider a vertical center for the image (where the translation should be zero)
            val center = half_screen_height - container_height_pixels / 2

            // get the location (x,y) of the image container in pixels
            val loc_screen = intArrayOf(0, 0)
            imgContainer.getLocationOnScreen(loc_screen)

            // trying to transform the current image container location into percentage
            // so when the image container is exaclty in the middle of the screen percentage should be zero
            // and as the image container getting closer to the edges of the screen should increase to 100%
            val final_loc = (loc_screen[1] - center) * 100 / half_screen_height

            // translate the inner image taking consideration also the density of the screen
            sliderView.setTranslationY(-final_loc * 1f * density)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun backGroundColor() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
            window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)

        }
        window.setBackgroundDrawableResource(R.drawable.bg_toolbar)
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun initSlider() {
        val file_maps = HashMap<String, String>()

        //slider
        sliderView = findViewById(R.id.imageSlider)
        adapterSlider = SliderAdapterDetail(this)
        sliderView.sliderAdapter = adapterSlider
        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM) //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
        sliderView.indicatorSelectedColor = getColor(R.color.colorPrimary)
        sliderView.indicatorUnselectedColor = Color.GRAY
        sliderView.isAutoCycle = false
        sliderView.scrollTimeInSec = 5
        renewItems(sliderView)
    }

    fun renewItems(view: View?) {
        val sliderItemList: MutableList<SliderDetailModel> = ArrayList<SliderDetailModel>()
        val images: MutableList<String> = ArrayList()

        if (!Img2.isEmpty()) {
            images.add(Img2)
        }
        if (!Img3.isEmpty()) {
            images.add(Img3)
        }
        if (!Img4.isEmpty()) {
            images.add(Img4)
        }


        //dummy data
        for (i in images.indices) {
            val sliderItem = SliderDetailModel()
            sliderItem.setDescription("دیجی مد")
            sliderItem.setImageUrl(images[i])
            sliderItemList.add(sliderItem)
        }
        adapterSlider.renewItems(sliderItemList)
    }

    override fun onRestart() {
        super.onRestart()
        if (ID !== "") {
            viewmodelDetail.get_Openion_product(ID)
            viewmodelDetail.mutable_Openion.observe(this, androidx.lifecycle.Observer {
                setupRecyclerViewOpenion(it)
            })

        }

    }
    override fun onResume() {
        super.onResume()
        ListOfNumberCart = AtomicReference(Paper.book("CartID").allKeys)

        if (!ListOfNumberCart.get().isEmpty()) {
            txt_countInCart.text = ListOfNumberCart.get().size.toString()
        } else {
            txt_countInCart.visibility = View.GONE
        }
        if (Paper.book("CartID").contains(ID)) {
            add_to_cart.text = "موجود در سبد خرید شما"
        } else {
            add_to_cart.text = "افزودن به سبد خرید"
        }
    }
}
