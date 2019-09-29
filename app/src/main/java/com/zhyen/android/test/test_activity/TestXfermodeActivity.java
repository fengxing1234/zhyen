package com.zhyen.android.test.test_activity;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhyen.android.R;
import com.zhyen.android.picture_selected.ui.widget.MediaGridInset;
import com.zhyen.android.test.test_widget.TestXformodeView;

import java.util.Arrays;
import java.util.List;

/**
 * 　　ADD:饱和相加,对图像饱和度进行相加,不常用
 * <p>
 * 　　CLEAR:清除图像
 * <p>
 * 　　DARKEN:变暗,较深的颜色覆盖较浅的颜色，若两者深浅程度相同则混合
 * <p>
 * 　　DST:只显示目标图像
 * <p>
 * 　　DST_ATOP:在源图像和目标图像相交的地方绘制【目标图像】，在不相交的地方绘制【源图像】，相交处的效果受到源图像和目标图像alpha的影响
 * <p>
 * 　　DST_IN:只在源图像和目标图像相交的地方绘制【目标图像】，绘制效果受到源图像对应地方透明度影响
 * <p>
 * 　　DST_OUT:只在源图像和目标图像不相交的地方绘制【目标图像】，在相交的地方根据源图像的alpha进行过滤，源图像完全不透明则完全过滤，完全透明则不过滤
 * <p>
 * 　　DST_OVER:将目标图像放在源图像上方
 * <p>
 * 　　LIGHTEN:变亮，与DARKEN相反，DARKEN和LIGHTEN生成的图像结果与Android对颜色值深浅的定义有关
 * <p>
 * 　　MULTIPLY:正片叠底，源图像素颜色值乘以目标图像素颜色值除以255得到混合后图像像素颜色值
 * <p>
 * 　　OVERLAY:叠加
 * <p>
 * 　　SCREEN:滤色，色调均和,保留两个图层中较白的部分，较暗的部分被遮盖
 * <p>
 * 　　SRC:只显示源图像
 * <p>
 * 　　SRC_ATOP:在源图像和目标图像相交的地方绘制【源图像】，在不相交的地方绘制【目标图像】，相交处的效果受到源图像和目标图像alpha的影响
 * <p>
 * 　　SRC_IN:只在源图像和目标图像相交的地方绘制【源图像】
 * <p>
 * 　　SRC_OUT:只在源图像和目标图像不相交的地方绘制【源图像】，相交的地方根据目标图像的对应地方的alpha进行过滤，目标图像完全不透明则完全过滤，完全透明则不过滤
 * <p>
 * 　　SRC_OVER:将源图像放在目标图像上方
 * <p>
 * 　　XOR:在源图像和目标图像相交的地方之外绘制它们，在相交的地方受到对应alpha和色值影响，如果完全不透明则相交处完全不绘制
 */
public class TestXfermodeActivity extends AppCompatActivity {
    /**
     * 1. 硬件加速对PorterDuffXferMode有影响，使用前请关闭硬件加速
     */

    private static final Xfermode[] sModes = {
            new PorterDuffXfermode(PorterDuff.Mode.CLEAR),
            new PorterDuffXfermode(PorterDuff.Mode.SRC),
            new PorterDuffXfermode(PorterDuff.Mode.DST),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
            new PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
            new PorterDuffXfermode(PorterDuff.Mode.DST_IN),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
            new PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
            new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
            new PorterDuffXfermode(PorterDuff.Mode.XOR),
            new PorterDuffXfermode(PorterDuff.Mode.DARKEN),
            new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
            new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
            new PorterDuffXfermode(PorterDuff.Mode.SCREEN),
            new PorterDuffXfermode(PorterDuff.Mode.ADD),
            new PorterDuffXfermode(PorterDuff.Mode.OVERLAY)
    };

    private static final String[] sLabels = {
            "Clear", "Src", "Dst", "SrcOver",
            "DstOver", "SrcIn", "DstIn", "SrcOut",
            "DstOut", "SrcATop", "DstATop", "Xor",
            "Darken", "Lighten", "Multiply", "Screen",
            "add", "overlay"
    };


    private XfermodeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_xfermode);
        RecyclerView recyclerView = findViewById(R.id.test_recycle_view);
        List<Xfermode> list = Arrays.asList(sModes);
        List<String> strings = Arrays.asList(sLabels);
        adapter = new XfermodeAdapter(list, strings);
        recyclerView.addItemDecoration(new MediaGridInset(3, 4, false));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
    }

    private class XfermodeAdapter extends RecyclerView.Adapter<XfermodeHolder> {

        private List<Xfermode> list;
        private List<String> names;

        public XfermodeAdapter(List<Xfermode> sModes, List<String> strings) {
            this.list = sModes;
            this.names = strings;
        }

        @NonNull
        @Override
        public XfermodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item_xfermode, parent, false);
            return new XfermodeHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull XfermodeHolder holder, int position) {
            holder.name.setText(names.get(position));
            holder.xformodeView.setXfermode(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private class XfermodeHolder extends RecyclerView.ViewHolder {

        private final TestXformodeView xformodeView;
        private final TextView name;

        public XfermodeHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.test_tv_xfermode_name);
            xformodeView = itemView.findViewById(R.id.test_xformode);
        }
    }
}
