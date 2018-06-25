package com.jinke.community.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.ui.toast.dialog.SpotsDialog;

import www.jinke.com.library.utils.LoadingUtils;
import www.jinke.com.library.utils.commont.NetWorksUtils;
import www.jinke.com.library.widget.ProgressWheel;


/**
 * Created by bruce on 8/19/15.
 */
public class LoadingLayout extends FrameLayout implements View.OnClickListener {
    private int state;
    private Context mContext;
    //是否一开始显示contentview，默认不显示
    private boolean isFirstVisible;
    private View defineLoadingPage;

    /**
     * 界面对应控件
     */
    public final static int Success = 0;
    private View contentView;

    public final static int Empty = 1;
    private View emptyPage;
    private ImageView emptyImg;
    private TextView emptyText;
    private LinearLayout emptyReload;

    public final static int Error = 2;
    private View errorPage;
    private ImageView errorImg;
    private TextView errorText;
    private TextView errorReloadBtn;

    public final static int No_Network = 3;//网络错误界面
    private View networkPage;
    private TextView txNetworkErrRefresh;
    private TextView txNetworkErrReturn;
    private TextView txNetworkErrCode;
    private ImageView networkImg;
    private OnReloadListener listener;

    public final static int Loading = 4;
    private View loadingPage;

    public final static int MyHouse_Empty = 5;//我的房屋界面为空
    private View myHouseEmptyPage;
    private TextView patrol_add;
    private TextView addHouseQuestion;
    private MyHouseListener myHouseListener;

    public final static int WithHold_Empty_Payment = 6;//待缴费界面为空
    private View withholdEmptyPage;

    public final static int Vehicle_Empty = 7;//车辆管理为空界面
    private View vehicleEmptyPage;
    private TextView txVehicleTips;

    public final static int Bind_House_Loading = 8;//绑定房屋加载界面
    private View bindLoadingPage;

    public final static int Bind_House_Yes = 9;//绑定房屋成功界面
    private View bindYesPage;
    private LinearLayout llStart;
    private OnBindYeslistener yesListener;

    public final static int Bind_House_No = 10;////绑定房屋失败界面
    private View bindNoPage;
    private TextView txReturn;
    private OnBindNolistener noListener;

    public final static int Community_Empty = 11;//选择社区为空界面
    private View communityEmptyPage;

    public final static int Authorized_Empty = 12;//授权为空界面
    private View authorizedEmptyPage;

    public final static int Pay_Recorder_Empty = 13;//缴费记录为空界面
    private View payRecorderEmptyPage;
    private TextView txPayrecorderEmpty;

    public final static int Break_stage_Empty = 14;//爆料平台/记录为空界面
    private View breakStageEmptyPage;
    private TextView txBreakstageTips;//提示
    private TextView txBreakstageAdd;//添加爆料
    private AddBreakListener addBreakListener;

    public final static int Announcement_Empty = 15;//通知公告
    private View announcementEmptyPage;

    //配置
    private static Config mConfig = new Config();
    private static String emptyStr = "暂无数据";
    private static String errorStr = "加载失败，请稍后重试···";
    private static String netwrokStr = "无网络连接，请检查网络···";
    private static String reloadBtnStr = "点击重试";
    private static int emptyImgId = R.drawable.icon_empty_img;
    private static int errorImgId = R.drawable.icon_layout_empty_wifi;
    private static int networkImgId = R.mipmap.layout_empty_wifi;
    private static int reloadBtnId = R.drawable.selector_btn_back_gray;
    private static int tipTextSize = 14;
    private static int buttonTextSize = 14;
    private static int tipTextColor = R.color.black;
    private static int buttonTextColor = R.color.base_text_color_light;
    private static int buttonWidth = -1;
    private static int buttonHeight = -1;
    private ProgressWheel progressWheel;
    private static int loadingLayoutId = R.layout.widget_loading_page;
    private static int backgroundColor = R.color.base_loading_background;

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingLayout);
        isFirstVisible = a.getBoolean(R.styleable.LoadingLayout_isFirstVisible, false);
        a.recycle();
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public LoadingLayout(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            throw new IllegalStateException("LoadingLayout can host only one direct child");
        }
        contentView = this.getChildAt(0);
        if (!isFirstVisible) {
            contentView.setVisibility(View.GONE);
        }
        build();
    }

    private void build() {
        //加载界面
        loadingPage = LayoutInflater.from(mContext).inflate(R.layout.widget_loading_page, null);
        defineLoadingPage = null;
        progressWheel = LoadingUtils.findViewById(loadingPage, R.id.progressBarTwo);

        //错误界面
        errorPage = LayoutInflater.from(mContext).inflate(R.layout.widget_error_page, null);
        errorText = LoadingUtils.findViewById(errorPage, R.id.error_text);
        errorImg = LoadingUtils.findViewById(errorPage, R.id.error_img);
        errorReloadBtn = LoadingUtils.findViewById(errorPage, R.id.error_reload_btn_aa);
        errorReloadBtn.setOnClickListener(this);

        //数据为空界面
        emptyPage = LayoutInflater.from(mContext).inflate(R.layout.widget_empty_page, null);
        emptyText = LoadingUtils.findViewById(emptyPage, R.id.empty_text);
        emptyReload = LoadingUtils.findViewById(emptyPage, R.id.empty_reload);
        emptyImg = LoadingUtils.findViewById(emptyPage, R.id.empty_img);
        emptyReload.setOnClickListener(this);

        //网络错误page
        networkPage = LayoutInflater.from(mContext).inflate(R.layout.widget_network_err_page, null);
        txNetworkErrRefresh = LoadingUtils.findViewById(networkPage, R.id.tx_network_err_refresh);
        txNetworkErrReturn = LoadingUtils.findViewById(networkPage, R.id.tx_network_err_return);
        txNetworkErrCode = LoadingUtils.findViewById(networkPage, R.id.tx_network_err_code);
        networkImg = LoadingUtils.findViewById(networkPage, R.id.no_network_img);
        txNetworkErrRefresh.setOnClickListener(this);
        txNetworkErrReturn.setOnClickListener(this);

        //房屋列表为空
        myHouseEmptyPage = LayoutInflater.from(mContext).inflate(R.layout.widget_myhouse_empty, null);
        addHouseQuestion = LoadingUtils.findViewById(myHouseEmptyPage, R.id.tx_question);
        patrol_add = LoadingUtils.findViewById(myHouseEmptyPage, R.id.patrol_add);
        addHouseQuestion.setOnClickListener(this);
        patrol_add.setOnClickListener(this);

        //待缴费界面为空
        withholdEmptyPage = LayoutInflater.from(mContext).inflate(R.layout.widget_payment_empty_page, null);

        //车辆管理为空界面
        vehicleEmptyPage = LayoutInflater.from(mContext).inflate(R.layout.widget_vehicle_empty_page, null);
        txVehicleTips = LoadingUtils.findViewById(vehicleEmptyPage, R.id.tx_no_info_tip_top);//没有车辆信息提示

        //绑定房屋加载界面
        bindLoadingPage = LayoutInflater.from(mContext).inflate(R.layout.widget_bind_house_loading, null);

        //绑定房屋成功界面
        bindYesPage = LayoutInflater.from(mContext).inflate(R.layout.widget_bind_house_yes, null);
        llStart = LoadingUtils.findViewById(bindYesPage, R.id.ll_start);//绑定成功的立即开启
        llStart.setOnClickListener(this);

        //绑定房屋失败界面
        bindNoPage = LayoutInflater.from(mContext).inflate(R.layout.widget_bind_house_no_house, null);
        txReturn = LoadingUtils.findViewById(bindNoPage, R.id.tx_return);//没有房屋的返回管家按钮
        txReturn.setOnClickListener(this);

        //选择小区为空界面
        communityEmptyPage = LayoutInflater.from(mContext).inflate(R.layout.widget_community_empty, null);

        //授权为空界面
        authorizedEmptyPage = LayoutInflater.from(mContext).inflate(R.layout.widget_authorized_empty, null);

        //缴费记录为空界面
        payRecorderEmptyPage = LayoutInflater.from(mContext).inflate(R.layout.widget_payrecorder_empty, null);
        txPayrecorderEmpty = LoadingUtils.findViewById(payRecorderEmptyPage, R.id.tx_payrecorder_empty);

        //爆料平台/记录为空界面
        breakStageEmptyPage = LayoutInflater.from(mContext).inflate(R.layout.widget_breakstage_empty, null);
        txBreakstageTips = LoadingUtils.findViewById(breakStageEmptyPage, R.id.tx_breakstage_empty);
        txBreakstageAdd = LoadingUtils.findViewById(breakStageEmptyPage, R.id.tx_breakstage_add);
        txBreakstageAdd.setOnClickListener(this);

        //通知公告
        announcementEmptyPage = LayoutInflater.from(mContext).inflate(R.layout.widget_announcement_empty, null);

        loadingPage.setBackgroundColor(LoadingUtils.getColor(mContext, backgroundColor));
        errorPage.setBackgroundColor(LoadingUtils.getColor(mContext, backgroundColor));
        emptyPage.setBackgroundColor(LoadingUtils.getColor(mContext, backgroundColor));
        networkPage.setBackgroundColor(LoadingUtils.getColor(mContext, backgroundColor));
        myHouseEmptyPage.setBackgroundColor(LoadingUtils.getColor(mContext, backgroundColor));
//        withholdEmptyPage.setBackgroundColor(LoadingUtils.getColor(mContext, backgroundColor));
        vehicleEmptyPage.setBackgroundColor(LoadingUtils.getColor(mContext, backgroundColor));
        bindLoadingPage.setBackgroundColor(LoadingUtils.getColor(mContext, backgroundColor));
        bindYesPage.setBackgroundColor(LoadingUtils.getColor(mContext, backgroundColor));
        bindNoPage.setBackgroundColor(LoadingUtils.getColor(mContext, backgroundColor));
        communityEmptyPage.setBackgroundColor(LoadingUtils.getColor(mContext, backgroundColor));
        authorizedEmptyPage.setBackgroundColor(LoadingUtils.getColor(mContext, backgroundColor));
        payRecorderEmptyPage.setBackgroundColor(LoadingUtils.getColor(mContext, backgroundColor));
        breakStageEmptyPage.setBackgroundColor(LoadingUtils.getColor(mContext, backgroundColor));
        announcementEmptyPage.setBackgroundColor(LoadingUtils.getColor(mContext, backgroundColor));

        styleRandom(progressWheel, mContext);
        progressWheel.resetCount();
        progressWheel.setText("JK");
        progressWheel.startSpinning();
        this.addView(networkPage);
        this.addView(emptyPage);
        this.addView(errorPage);
        this.addView(loadingPage);
        this.addView(myHouseEmptyPage);
        this.addView(withholdEmptyPage);
        this.addView(vehicleEmptyPage);
        this.addView(bindLoadingPage);
        this.addView(bindNoPage);
        this.addView(bindYesPage);
        this.addView(communityEmptyPage);
        this.addView(authorizedEmptyPage);
        this.addView(payRecorderEmptyPage);
        this.addView(breakStageEmptyPage);
        this.addView(announcementEmptyPage);

        setStatus(Loading);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_start:
                if (yesListener != null) {
                    yesListener.bindYes(v);
                }
                break;

            case R.id.tx_return:
                if (noListener != null) {
                    noListener.bindNo(v);
                }
                break;

            case R.id.error_reload_btn_aa:
                if (listener != null) {
                    listener.onReload(v);
                }
                break;
            case R.id.tx_breakstage_add:
                if (addBreakListener != null) {
                    addBreakListener.addBreak(v);
                }
                break;

            case R.id.tx_question:
                if (myHouseListener != null) {
                    myHouseListener.myHouse(v);
                }
                break;
        }
    }

    /**
     * 加载动画
     *
     * @param wheel
     * @param ctx
     */
    private void styleRandom(ProgressWheel wheel, Context ctx) {
        wheel.setRimShader(null);
        wheel.setRimColor(0xFFFFFFFF);
        wheel.setCircleColor(0x00000000);
        wheel.setBarColor(0xFF000000);
        wheel.setContourColor(0xFFFFFFFF);
        wheel.setBarWidth(pxFromDp(ctx, 1));
        wheel.setBarLength(pxFromDp(ctx, 100));
        wheel.setSpinSpeed(4f);
        wheel.setDelayMillis(3);
    }

    public static int pxFromDp(final Context context, final float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public View getLoadingPltrolAdd() {
        return myHouseEmptyPage;
    }

    public SpotsDialog progressDialog;

    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void setStatus(@Flavour int status) {
        hideLoading();
        this.state = status;

        bindLoadingPage.setVisibility(GONE);
        bindNoPage.setVisibility(GONE);
        bindYesPage.setVisibility(GONE);
        contentView.setVisibility(View.GONE);
        loadingPage.setVisibility(View.GONE);
        emptyPage.setVisibility(View.GONE);
        errorPage.setVisibility(View.GONE);
        networkPage.setVisibility(View.GONE);
        myHouseEmptyPage.setVisibility(View.GONE);
        withholdEmptyPage.setVisibility(View.GONE);
        vehicleEmptyPage.setVisibility(GONE);
        communityEmptyPage.setVisibility(GONE);
        authorizedEmptyPage.setVisibility(GONE);
        payRecorderEmptyPage.setVisibility(GONE);
        breakStageEmptyPage.setVisibility(GONE);
        announcementEmptyPage.setVisibility(GONE);
        if (defineLoadingPage != null) {
            defineLoadingPage.setVisibility(View.GONE);
        } else {
            loadingPage.setVisibility(View.GONE);
        }
        if (!NetWorksUtils.isConnected(mContext)) {
            networkPage.setVisibility(View.VISIBLE);
            return;
        }
        switch (status) {
            case Success:
                contentView.setVisibility(VISIBLE);
                break;

            case Loading:
                if (defineLoadingPage != null) {
                    defineLoadingPage.setVisibility(VISIBLE);
                }
                if (progressDialog == null) {
                    progressDialog = new SpotsDialog(mContext);
                    progressDialog.setCanceledOnTouchOutside(true);
                }
                progressDialog.show();
                break;

            case Empty:
                emptyPage.setVisibility(VISIBLE);
                break;

            case Error:
                errorPage.setVisibility(View.VISIBLE);
                break;

            case No_Network:
                networkPage.setVisibility(View.VISIBLE);
                break;

            case MyHouse_Empty:
                myHouseEmptyPage.setVisibility(View.VISIBLE);
                break;

            case WithHold_Empty_Payment:
                withholdEmptyPage.setVisibility(View.VISIBLE);
                break;

            case Bind_House_Loading:
                bindLoadingPage.setVisibility(VISIBLE);
                break;

            case Bind_House_No:
                bindNoPage.setVisibility(VISIBLE);
                break;

            case Bind_House_Yes:
                bindYesPage.setVisibility(VISIBLE);
                break;

            case Vehicle_Empty:
                vehicleEmptyPage.setVisibility(VISIBLE);
                break;

            case Community_Empty:
                communityEmptyPage.setVisibility(VISIBLE);
                break;

            case Authorized_Empty:
                authorizedEmptyPage.setVisibility(VISIBLE);
                break;

            case Pay_Recorder_Empty:
                payRecorderEmptyPage.setVisibility(VISIBLE);
                break;

            case Break_stage_Empty:
                breakStageEmptyPage.setVisibility(VISIBLE);
                break;

            case Announcement_Empty:
                announcementEmptyPage.setVisibility(VISIBLE);
                break;
        }
    }

    /**
     * 返回当前状态{Success, Empty, Error, No_Network, Loading}
     *
     * @return
     */
    public int getStatus() {
        return state;
    }

    @IntDef({Success, Empty, Error, No_Network, Loading, MyHouse_Empty, WithHold_Empty_Payment, Vehicle_Empty,
            Bind_House_Loading, Bind_House_No, Bind_House_Yes, Community_Empty, Authorized_Empty, Pay_Recorder_Empty,
            Break_stage_Empty, Announcement_Empty})
    public @interface Flavour {
    }

    /**
     * 设置ReloadButton的监听器
     *
     * @param listener
     * @return
     */
    public LoadingLayout setOnReloadListener(OnReloadListener listener) {
        this.listener = listener;
        return this;
    }

    public interface OnReloadListener {
        void onReload(View v);
    }

    /**
     * 设置绑定房屋yes和no的监听
     */
    public LoadingLayout setBindYesListener(OnBindYeslistener listener) {
        this.yesListener = listener;
        return this;
    }

    public LoadingLayout setBindNoListener(OnBindNolistener listener) {
        this.noListener = listener;
        return this;
    }

    public interface OnBindYeslistener {
        void bindYes(View v);
    }

    public interface OnBindNolistener {
        void bindNo(View v);
    }

    /**
     * 设置爆料平台/记录为空界面的添加爆料监听器
     *
     * @param listener
     * @return
     */
    public LoadingLayout setAddBreakListener(AddBreakListener listener) {
        this.addBreakListener = listener;
        return this;
    }

    public interface AddBreakListener {
        void addBreak(View v);
    }

    /**
     * 设置我的房屋为空界面监听器
     *
     * @param listener
     * @return
     */
    public LoadingLayout setMyHouseListener(MyHouseListener listener) {
        this.myHouseListener = listener;
        return this;
    }

    public interface MyHouseListener {
        void myHouse(View v);
    }

    /**
     * 设置文字和图片
     */
    //设置报料台/爆料记录的提示文字
    public LoadingLayout setBreakPageTips(String tips) {
        txBreakstageTips.setText(tips);
        return this;
    }

    //设置报料台/爆料记录的按钮文字
    public LoadingLayout setBreakPageButton(String buttontx) {
        txBreakstageAdd.setText(buttontx);
        return this;
    }

    //设置报料台/爆料记录的按钮文字
    public LoadingLayout setPayrecorderTips(String tips) {
        txPayrecorderEmpty.setText(tips);
        return this;
    }

    //设置报料台/爆料记录的按钮可见性
    public LoadingLayout setBreakPageButtonVisibility(int visibility) {
        txBreakstageAdd.setVisibility(visibility);
        return this;
    }

    //设置车辆月租提示文字
    public LoadingLayout setVehicleTips(String tips) {
        txVehicleTips.setText(tips);
        return this;
    }


    //设置网络问题的按钮可见性
    public LoadingLayout setNetWorkRefreshVisibility(int visibility) {
        txNetworkErrRefresh.setVisibility(visibility);
        return this;
    }

    public LoadingLayout setNetWorkReturnVisibility(int visibility) {
        txNetworkErrReturn.setVisibility(visibility);
        return this;
    }


    /**
     * 设置Empty状态提示文本，仅对当前所在的地方有效
     *
     * @param text
     * @return
     */
    public LoadingLayout setEmptyText(String text) {
        emptyText.setText(text);
        return this;
    }

    /**
     * 设置Error状态提示文本，仅对当前所在的地方有效
     *
     * @param text
     * @return
     */
    public LoadingLayout setErrorText(String text) {

        errorText.setText(text);
        return this;
    }

    /**
     * 设置No_Network状态提示文本，仅对当前所在的地方有效
     *
     * @param text
     * @return
     */
    public LoadingLayout setNoNetworkText(String text) {

        txNetworkErrCode.setText(text);
        return this;
    }

    /**
     * 设置Empty状态显示图片，仅对当前所在的地方有效
     *
     * @param id
     * @return
     */
    public LoadingLayout setEmptyImage(@DrawableRes int id) {


        emptyImg.setImageResource(id);
        return this;
    }

    /**
     * 设置Error状态显示图片，仅对当前所在的地方有效
     *
     * @param id
     * @return
     */
    public LoadingLayout setErrorImage(@DrawableRes int id) {

        errorImg.setImageResource(id);
        return this;
    }

    /**
     * 设置No_Network状态显示图片，仅对当前所在的地方有效
     *
     * @param id
     * @return
     */
    public LoadingLayout setNoNetworkImage(@DrawableRes int id) {

        networkImg.setImageResource(id);
        return this;
    }

    /**
     * 设置Empty状态提示文本的字体大小，仅对当前所在的地方有效
     *
     * @param sp
     * @return
     */
    public LoadingLayout setEmptyTextSize(int sp) {

        emptyText.setTextSize(sp);
        return this;
    }

    /**
     * 设置Error状态提示文本的字体大小，仅对当前所在的地方有效
     *
     * @param sp
     * @return
     */
    public LoadingLayout setErrorTextSize(int sp) {

        errorText.setTextSize(sp);
        return this;
    }

    /**
     * 设置No_Network状态提示文本的字体大小，仅对当前所在的地方有效
     *
     * @param sp
     * @return
     */
    public LoadingLayout setNoNetworkTextSize(int sp) {
        txNetworkErrCode.setTextSize(sp);
        return this;
    }

    /**
     * 设置Empty状态图片的显示与否，仅对当前所在的地方有效
     *
     * @param bool
     * @return
     */
    public LoadingLayout setEmptyImageVisible(boolean bool) {
        if (bool) {
            emptyImg.setVisibility(View.VISIBLE);
        } else {
            emptyImg.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 设置Error状态图片的显示与否，仅对当前所在的地方有效
     *
     * @param bool
     * @return
     */
    public LoadingLayout setErrorImageVisible(boolean bool) {

        if (bool) {
            errorImg.setVisibility(View.VISIBLE);
        } else {
            errorImg.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 设置No_Network状态图片的显示与否，仅对当前所在的地方有效
     *
     * @param bool
     * @return
     */
    public LoadingLayout setNoNetworkImageVisible(boolean bool) {

        if (bool) {
            networkImg.setVisibility(View.VISIBLE);
        } else {
            networkImg.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 设置ReloadButton的文本，仅对当前所在的地方有效
     *
     * @param text
     * @return
     */
    public LoadingLayout setReloadButtonText(@NonNull String text) {

        errorReloadBtn.setText(text);
        txNetworkErrRefresh.setText(text);
        return this;
    }

    /**
     * 设置ReloadButton的文本字体大小，仅对当前所在的地方有效
     *
     * @param sp
     * @return
     */
    public LoadingLayout setReloadButtonTextSize(int sp) {

        errorReloadBtn.setTextSize(sp);
        txNetworkErrRefresh.setTextSize(sp);
        return this;
    }

    /**
     * 设置ReloadButton的文本颜色，仅对当前所在的地方有效
     *
     * @param id
     * @return
     */
    public LoadingLayout setReloadButtonTextColor(@ColorRes int id) {

        errorReloadBtn.setTextColor(LoadingUtils.getColor(mContext, id));
        txNetworkErrRefresh.setTextSize(LoadingUtils.getColor(mContext, id));
        return this;
    }

    /**
     * 设置ReloadButton的背景，仅对当前所在的地方有效
     *
     * @param id
     * @return
     */
    public LoadingLayout setReloadButtonBackgroundResource(@DrawableRes int id) {

        errorReloadBtn.setBackgroundResource(id);
        txNetworkErrRefresh.setBackgroundResource(id);
        return this;
    }


    /**
     * 自定义加载页面，仅对当前所在的Activity有效
     *
     * @param view
     * @return
     */
    public LoadingLayout setLoadingPage(View view) {

        defineLoadingPage = view;
        this.removeView(loadingPage);
        defineLoadingPage.setVisibility(View.GONE);
        this.addView(view);
        return this;
    }

    /**
     * 自定义加载页面，仅对当前所在的地方有效
     *
     * @param id
     * @return
     */
    public LoadingLayout setLoadingPage(@LayoutRes int id) {

        this.removeView(loadingPage);
        View view = LayoutInflater.from(mContext).inflate(id, null);
        defineLoadingPage = view;
        defineLoadingPage.setVisibility(View.GONE);
        this.addView(view);
        return this;
    }

    /**
     * 获取当前自定义的loadingpage
     *
     * @return
     */
    public View getLoadingPage() {

        return defineLoadingPage;
    }


    /**
     * 获取全局使用的loadingpage
     *
     * @return
     */
    public View getGlobalLoadingPage() {
        return loadingPage;
    }

    /**
     * 获取全局配置的class
     *
     * @return
     */
    public static Config getConfig() {

        return mConfig;
    }

    /**
     * 全局配置的Class，对所有使用到的地方有效
     */
    public static class Config {

        public Config setErrorText(@NonNull String text) {

            errorStr = text;
            return mConfig;
        }

        public Config setEmptyText(@NonNull String text) {

            emptyStr = text;
            return mConfig;
        }

        public Config setNoNetworkText(@NonNull String text) {

            netwrokStr = text;
            return mConfig;
        }

        public Config setReloadButtonText(@NonNull String text) {

            reloadBtnStr = text;
            return mConfig;
        }

        /**
         * 设置所有提示文本的字体大小
         *
         * @param sp
         * @return
         */
        public Config setAllTipTextSize(int sp) {

            tipTextSize = sp;
            return mConfig;
        }

        /**
         * 设置所有提示文本的字体颜色
         *
         * @param color
         * @return
         */
        public Config setAllTipTextColor(@ColorRes int color) {

            tipTextColor = color;
            return mConfig;
        }

        public Config setReloadButtonTextSize(int sp) {

            buttonTextSize = sp;
            return mConfig;
        }

        public Config setReloadButtonTextColor(@ColorRes int color) {

            buttonTextColor = color;
            return mConfig;
        }

        public Config setReloadButtonBackgroundResource(@DrawableRes int id) {

            reloadBtnId = id;
            return mConfig;
        }

        public Config setReloadButtonWidthAndHeight(int width_dp, int height_dp) {

            buttonWidth = width_dp;
            buttonHeight = height_dp;
            return mConfig;
        }

        public Config setErrorImage(@DrawableRes int id) {

            errorImgId = id;
            return mConfig;
        }

        public Config setEmptyImage(@DrawableRes int id) {

            emptyImgId = id;
            return this;
        }

        public Config setNoNetworkImage(@DrawableRes int id) {

            networkImgId = id;
            return mConfig;
        }

        public Config setLoadingPageLayout(@LayoutRes int id) {

            loadingLayoutId = id;
            return mConfig;
        }

        public Config setAllPageBackgroundColor(@ColorRes int color) {

            backgroundColor = color;
            return mConfig;
        }
    }
}

