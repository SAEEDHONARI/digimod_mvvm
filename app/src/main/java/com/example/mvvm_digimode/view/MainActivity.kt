package com.example.mvvm_digimode.view
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_digimode.R
import com.example.mvvm_digimode.Repository.App
import com.example.mvvm_digimode.Repository.Factory
import com.example.mvvm_digimode.Utils.ClickItem
import com.example.mvvm_digimode.ViewModel.ViewModel_listPosts
import com.example.mvvm_digimode.model.Banner_Model
import com.example.mvvm_digimode.model.Product_Model
import com.example.mvvm_digimode.view.MainSlider.ImageResourceAdapter
import com.example.mvvm_digimode.view.MainSlider.ImageResourceViewHolder
import com.google.android.material.navigation.NavigationView
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.indicator.enums.IndicatorSlideMode
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(),ClickItem {

   //sliderMain
    protected var mPictureList: MutableList<String> = ArrayList()
     lateinit var mViewPager: BannerViewPager<String, ImageResourceViewHolder>


    private var dl: DrawerLayout? = null
    private var nv: NavigationView? = null
    private var tg: ActionBarDrawerToggle? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            backGroundColor()
        }
        setContentView(R.layout.activity_main)


        initSliderView()
        val viewmodel
                = ViewModelProvider(this,Factory(App())).get(ViewModel_listPosts::class.java)

        viewmodel.get_banner_product()
        viewmodel.mutable_Banner.observe(this, Observer {
            setupMainSlider(it as MutableList<Banner_Model>);
        })


        viewmodel.get_Discount_product()
        viewmodel.mutable_Discount.observe(this, Observer {
            setupRecyclerView(circle_recycler,it)
        })



        viewmodel.get_Season_Product()
        viewmodel.mutable_Season.observe(this, Observer {
            setupRecyclerView(season_recycler,it)
        })

        viewmodel.get_Most_sell("10")
        viewmodel.mutable_Mostsell.observe(this, Observer {
            setupRecyclerView(most_sale_recycler,it)

        })
    }



    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupMainSlider(list:MutableList<Banner_Model>) {
        mViewPager
            .setPageMargin(resources.getDimensionPixelOffset(R.dimen.dp_15))
            .setRevealWidth(resources.getDimensionPixelOffset(R.dimen.dp_15))
            .create(getPicList(4,list));
        mViewPager.removeDefaultPageTransformer()
    }

    private fun getPicList(i: Int, list: MutableList<Banner_Model>): MutableList<String> {
        mPictureList.clear()

        for (j in 0..i ) {
            mPictureList.add(j, list.get(j).url)
        }
        return mPictureList
    }

    private fun setupRecyclerView(recycler :RecyclerView,list: List<Product_Model>) {

        recycler.also {
            it.layoutManager=LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            if (recycler==circle_recycler) {
                val adapter=Adapter_Discount_RecyclerView(list,this)
                it.adapter=adapter

            } else {
                val adapter=
                    Adapter_Horizontal_Recycler(list,this)
                it.adapter=adapter
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)

      //  if (Paper.book().contains("Username") && Paper.book().contains("Pass")) {
            menu.findItem(R.id.app_bar_profile).setVisible(true)
     //   } else {
         //   menu.findItem(R.id.app_bar_profile).setVisible(false)
       // }


    //    var ListOfNumberCart :List<String> =Paper.book("CartID").allKeys



        var item = menu.findItem(R.id.app_bar_shopingcart_toolbar)
        MenuItemCompat.setActionView(item, R.layout.shoping_cart_layout)

        var notifCount = MenuItemCompat.getActionView(item) as RelativeLayout




    //    tv_numberCart = notifCount.findViewById(R.id.actionbar_notifcation_textview)
       // if (!ListOfNumberCart.isEmpty()) {

         //   tv_numberCart.setText((ListOfNumberCart.size).toString())
      //  }else{
       //     tv_numberCart.visibility=View.GONE
     //   }
       var Img: ImageView =notifCount.findViewById(R.id.app_bar_search_numberic)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.app_bar_search) {
            //val searchIntent = Intent(this, SearchActivity::class.java)
           // this.startActivity(searchIntent)
        } else if (id == R.id.app_bar_profile) {
            //val profileIntent = Intent(this, ProfileUserActivity::class.java)
            //this.startActivity(profileIntent)
        } else if (id == R.id.app_bar_shopingcart_toolbar) {
          //  val CartIntent = Intent(this, CartActivity::class.java)
          //  this.startActivity(CartIntent)
        }

        return if (tg!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
    private fun getmenu() {
        nv = findViewById(R.id.nv)



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        }


        val mtoolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(mtoolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        dl = findViewById<View>(R.id.dl) as DrawerLayout
        dl!!.closeDrawers()
        tg = ActionBarDrawerToggle(this, dl, R.string.open, R.string.close)
        dl!!.addDrawerListener(tg!!)
        tg!!.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val headerview: View = nv!!.getHeaderView(0)

        //set color of menu indicator
        tg!!.drawerArrowDrawable.color = Color.WHITE
        nv!!.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId


            val num: Int = id


            true
        })

    }

    override fun onResume() {
        super.onResume()
        getmenu()
        if (mViewPager != null) {
            mViewPager!!.startLoop()
        }
    }

    override fun onPause() {
        super.onPause()
        if (mViewPager != null) {
            mViewPager!!.stopLoop()
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

    @RequiresApi(Build.VERSION_CODES.M)
    protected fun initSliderView() {
        mViewPager = findViewById(R.id.banner_view)
        mViewPager
            .setIndicatorSlideMode(IndicatorSlideMode.SCALE)
            .setIndicatorSliderColor(
                getColor(R.color.red_normal_color),
                getColor(R.color.red_checked_color)
            )
            .setIndicatorSliderRadius(
                resources.getDimensionPixelOffset(R.dimen.dp_4),
                resources.getDimensionPixelOffset(R.dimen.dp_5)
            )
            .setAdapter(ImageResourceAdapter(resources.getDimensionPixelOffset(R.dimen.dp_8)))
            .setInterval(3000)
    }

    override fun ClickItem
                (id: String, barcode: String, category: String, des: String, discount: Int
                 , pic1: String, pic2: String, pic3: String, pic4: String, price: String, title: String) {

        val mIntent = Intent(this, DetailActivity::class.java)
        mIntent.putExtra("id",id)
        mIntent.putExtra("barcode",barcode)
        mIntent.putExtra("category",category)
        mIntent.putExtra("des",des)
        mIntent.putExtra("discount",discount)
        mIntent.putExtra("pic1",pic1)
        mIntent.putExtra("pic2",pic2)
        mIntent.putExtra("pic3",pic3)
        mIntent.putExtra("pic4",pic4)
        mIntent.putExtra("price",price)
        mIntent.putExtra("title",title)

        startActivity(mIntent)
    }

}
