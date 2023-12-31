package com.whatswebdots.whatswebscannerpy.gbwhats.gb_adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.whatswebdots.whatswebscannerpy.gbwhats.R;
import com.whatswebdots.whatswebscannerpy.gbwhats.gb_activity.gb_FullViewActivity;
import com.whatswebdots.whatswebscannerpy.gbwhats.gb_activity.gb_VideoPlayerActivity;
import com.whatswebdots.whatswebscannerpy.gbwhats.gb_util.Utils;

import java.io.File;
import java.util.ArrayList;

public class ShowImagesAdapter extends PagerAdapter {

    private Context context;
    gb_FullViewActivity whatsappFullViewActivity;
    private ArrayList<File> imageList;
    private LayoutInflater inflater;

    public int getItemPosition(Object obj) {
        return -2;
    }

    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
    }

    public Parcelable saveState() {
        return null;
    }

    public ShowImagesAdapter(Context context2, ArrayList<File> arrayList, gb_FullViewActivity whatsappFullViewActivity2) {
        this.context = context2;
        this.imageList = arrayList;
        this.whatsappFullViewActivity = whatsappFullViewActivity2;
        this.inflater = LayoutInflater.from(context2);
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    public Object instantiateItem(ViewGroup viewGroup, final int i) {
        View inflate = this.inflater.inflate(R.layout.gb_slidingimages_layout, viewGroup, false);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.im_vpPlay);
        ImageView imageView2 = (ImageView) inflate.findViewById(R.id.im_share);
        ImageView imageView3 = (ImageView) inflate.findViewById(R.id.im_delete);
        Glide.with(this.context).load(this.imageList.get(i).getPath()).into((ImageView) inflate.findViewById(R.id.im_fullViewImage));
        viewGroup.addView(inflate, 0);
        String substring = this.imageList.get(i).getName().substring(this.imageList.get(i).getName().lastIndexOf("."));
        if (substring.equals(".mp4") || substring.equals(".mov")) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                Intent intent = new Intent(context, gb_VideoPlayerActivity.class);
                intent.putExtra("PathVideo", imageList.get(i).getPath());
                context.startActivity(intent);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (((File) ShowImagesAdapter.this.imageList.get(i)).delete()) {
                    ShowImagesAdapter.this.whatsappFullViewActivity.deleteFileAA(i);
                }
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (((File) ShowImagesAdapter.this.imageList.get(i)).getName().substring(((File) ShowImagesAdapter.this.imageList.get(i)).getName().lastIndexOf(".")).equals(".mp4")) {
                    Utils.shareVideo(ShowImagesAdapter.this.context, ((File) ShowImagesAdapter.this.imageList.get(i)).getPath());
                } else {
                    Utils.shareImage(ShowImagesAdapter.this.context, ((File) ShowImagesAdapter.this.imageList.get(i)).getPath());
                }
            }
        });
        return inflate;
    }

    public int getCount() {
        return this.imageList.size();
    }

    public boolean isViewFromObject(View view, Object obj) {
        return view.equals(obj);
    }
}
