package com.example.testkotlindemo.base

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Html
import android.util.TypedValue
import android.view.*
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import butterknife.ButterKnife
import com.blankj.utilcode.util.SizeUtils
import com.example.testkotlindemo.R

abstract class BaseActivity<T : IBasePresenter> : AppCompatActivity(), IBaseView<T> {
    protected var viewGroup: ViewGroup?=null
    protected var parentLinearLayout: LinearLayout?=null
    protected var tvToolbarCenterTitle: TextView? = null
    protected var tvToolbarRightText: TextView? = null
    protected var tbToolbar: Toolbar? = null

    private var frameLayout: FrameLayout? = null

    private var presenter: T? = null

    private var isShowDialog: Boolean = false

    protected val isShowCenterToolBarTitle: Boolean
        get() = true

    protected val isLightMode: Boolean
        get() = false

    /**
     * 获取主题色
     */
    val colorPrimary: Int
        get() {
            val typedValue = TypedValue()
            getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true)
            return typedValue.data
        }

    protected fun onCreate(@Nullable savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        trimMemory()
        initSystemBarTint()
        initContentView()
        initToolBar()
        setHideAppBar()
    }

    private fun initContentView() {
        viewGroup = findViewById(android.R.id.content)
        viewGroup!!.removeAllViews()
        parentLinearLayout = LinearLayout(this)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        parentLinearLayout!!.layoutParams = lp
        parentLinearLayout!!.orientation = LinearLayout.VERTICAL
        viewGroup!!.addView(parentLinearLayout)
        frameLayout = FrameLayout(this)
        frameLayout!!.layoutParams = lp
        val progressBar = ProgressBar(this)
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.gravity = Gravity.CENTER
        progressBar.layoutParams = layoutParams
        frameLayout!!.addView(progressBar)
        frameLayout!!.isClickable = true
        frameLayout!!.visibility = View.GONE
        frameLayout!!.setBackgroundColor(getResources().getColor(R.color.backgroundTransparent))
        viewGroup!!.addView(frameLayout)
        LayoutInflater.from(this).inflate(R.layout.toolbar_layout, parentLinearLayout, true)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initToolBar() {
        tbToolbar = findViewById(R.id.tb_toolbar)
        tvToolbarCenterTitle = findViewById(R.id.tv_toolbar_center_title)
        tvToolbarRightText = findViewById(R.id.tv_toolbar_right_text)
        if (!isShowCenterToolBarTitle) {
            tbToolbar!!.setTitle(getString(R.string.app_name))
        }
        setSupportActionBar(tbToolbar)
        if (getSupportActionBar() != null) {
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()!!.setHomeButtonEnabled(true)
            if (isShowCenterToolBarTitle) {
                getSupportActionBar()!!.setDisplayShowTitleEnabled(false)
            }
        }
        if (tbToolbar != null) {
            tbToolbar!!.setNavigationOnClickListener(View.OnClickListener { onBackPressed() })
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    protected fun setToolBarTitle(title: CharSequence) {
        if (tbToolbar != null) {
            tbToolbar!!.setTitle(title)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    protected fun setToolBarTitle(titleId: Int) {
        if (tbToolbar != null) {
            tbToolbar!!.setTitle(titleId)
        }
    }

    /**
     * 需要设置isShowCenterToolBarTitle 为true才生效
     *
     * @param title 标题
     */
    protected fun setCenterToolBarTitle(title: CharSequence) {
        if (tvToolbarCenterTitle != null) {
            tvToolbarCenterTitle!!.text = title
        }
    }

    protected fun setCenterToolBarTitle(titleId: Int) {
        if (tvToolbarCenterTitle != null) {
            tvToolbarCenterTitle!!.setText(titleId)
        }
    }

    protected fun setHideAppBar() {
        if (getSupportActionBar() == null) {
            return
        }
        getSupportActionBar()!!.hide()
    }

    protected fun setShowAppBar() {
        if (getSupportActionBar() == null) {
            return
        }
        getSupportActionBar()!!.show()
    }

    protected fun setShowToolBarRightText() {
        if (tvToolbarRightText != null) {
            tvToolbarRightText!!.visibility = View.VISIBLE
        }
    }

    protected fun setHideToolBarRightText() {
        if (tvToolbarRightText != null) {
            tvToolbarRightText!!.visibility = View.INVISIBLE
        }
    }

    protected fun setToolBarRightText(text: CharSequence) {
        if (tvToolbarRightText != null) {
            setShowToolBarRightText()
            tvToolbarRightText!!.text = text
        }
    }


    protected fun setToolBarRightIconResource(resId: Int) {
        if (tvToolbarRightText != null) {
            setShowToolBarRightText()

            val imageResId = "<img src='$resId'>"
            tvToolbarRightText!!.text = Html.fromHtml(imageResId, Html.ImageGetter { source ->
                val id = Integer.parseInt(source)
                val drawable = ContextCompat.getDrawable(this@BaseActivity, resId) ?: return@ImageGetter null
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight())
                drawable
            }, null)
        }
    }

    protected fun setToolBarRightText(text: Int) {
        if (tvToolbarRightText != null) {
            setShowToolBarRightText()
            tvToolbarRightText!!.setText(text)
        }
    }

    protected fun setTvToolbarRightTextColor(@ColorInt textColor: Int) {
        if (tvToolbarRightText != null) {
            tvToolbarRightText!!.setTextColor(textColor)
        }
    }

    protected fun setToolBarRightTextClick(onClickListener: View.OnClickListener?) {
        if (tvToolbarRightText != null && onClickListener != null) {
            tvToolbarRightText!!.setOnClickListener(onClickListener)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    protected fun showToolBarTitle(textId: Int) {
        setShowAppBar()
        setToolBarTitle(textId)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    protected fun showToolBarTitle(title: CharSequence) {
        setShowAppBar()
        setToolBarTitle(title)
    }

    protected fun setHideBackPressedIcon() {
        if (getSupportActionBar() != null) {
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(false)
            getSupportActionBar()!!.setHomeButtonEnabled(false)
        }
    }

    fun setShowBackPressedIcon() {
        if (getSupportActionBar() != null) {
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()!!.setHomeButtonEnabled(true)
        }
    }

    protected fun setToolBarRightDrawable(resId: Int) {
        if (tvToolbarRightText != null) {
            setShowToolBarRightText()
            val drawable = getResources().getDrawable(resId)
            tvToolbarRightText!!.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    protected fun setToolbarMenu() {
        setShowAppBar()
        setToolBarRightText(getResources().getString(R.string.app_name))
        setToolBarRightDrawable(R.mipmap.toolbar_right)
        setTvToolbarRightTextColor(Color.WHITE)
        if (tvToolbarRightText != null) {
            tvToolbarRightText!!.textSize = SizeUtils.sp2px(10f).toFloat()
            tvToolbarRightText!!.compoundDrawablePadding = 15
            val layoutParams = tvToolbarRightText!!.layoutParams as Toolbar.LayoutParams
            layoutParams.setMarginEnd(30)
            tvToolbarRightText!!.layoutParams = layoutParams
            tvToolbarRightText!!.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        }
    }


    override fun setContentView(layoutResID: Int) {
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true)

        viewGroup!!.bringChildToFront(frameLayout)

        ButterKnife.bind(this)
    }

    /**
     * 设置状态栏颜色
     */
    protected fun initSystemBarTint() {
        val window = getWindow()
        if (translucentStatusBar()) {
            // 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.setStatusBarColor(Color.TRANSPARENT)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
            return
        }
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(setStatusBarColor())
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decorView = window.getDecorView()
            var vis = decorView.getSystemUiVisibility()
            if (isLightMode) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                vis = vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                vis = vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            decorView.setSystemUiVisibility(vis)
        }
    }

    /**
     * 子类可以重写改变状态栏颜色
     */
    protected fun setStatusBarColor(): Int {
        return colorPrimary
    }

    /**
     * 子类可以重写决定是否使用透明状态栏
     */
    protected fun translucentStatusBar(): Boolean {
        return false
    }

    fun setPresenter(presenter: IBasePresenter?) {
        if (presenter != null) {
            this.presenter = presenter as T?
        }
    }

    private fun trimMemory() {
//        if ("SplashActivity" != getClass().getSimpleName() && 0 == Constant.STATUS_FORCE_KILLED) {
//            val intent = Intent(this, SplashActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//            android.os.Process.killProcess(android.os.Process.myPid())
//        }
    }

    protected override fun onDestroy() {
        super.onDestroy()

        if (presenter != null) {
            presenter!!.detachView()
        }
    }

    override fun showDialog() {
        if (frameLayout != null && frameLayout!!.visibility == View.GONE) {
            frameLayout!!.visibility = View.VISIBLE

            isShowDialog = true
        }
    }

    override fun dismissDialog() {
        if (frameLayout != null && frameLayout!!.visibility == View.VISIBLE) {
            frameLayout!!.visibility = View.GONE

            isShowDialog = false
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (isShowDialog && keyCode == KeyEvent.KEYCODE_BACK) {
            true
        } else super.onKeyDown(keyCode, event)
    }
}