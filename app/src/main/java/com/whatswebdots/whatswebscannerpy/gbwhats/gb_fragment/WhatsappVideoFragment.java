package com.whatswebdots.whatswebscannerpy.gbwhats.gb_fragment;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.whatswebdots.whatswebscannerpy.gbwhats.R;
import com.whatswebdots.whatswebscannerpy.gbwhats.gb_adapter.WhatsappStatusAdapter;
import com.whatswebdots.whatswebscannerpy.gbwhats.gb_interFace.FileListWhatsappClickInterface;
import com.whatswebdots.whatswebscannerpy.gbwhats.gb_interFace.WhatsappStatusModel;
import com.whatswebdots.whatswebscannerpy.gbwhats.gb_local.gb_AllKeyHub;
import com.whatswebdots.whatswebscannerpy.gbwhats.gb_local.gb_Internet_Alert;
import com.whatswebdots.whatswebscannerpy.gbwhats.gb_local.gb_NetworkUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class WhatsappVideoFragment extends Fragment implements FileListWhatsappClickInterface {
    private File[] allfiles;
    ArrayList<WhatsappStatusModel> statusModelArrayList;
    private WhatsappStatusAdapter whatsappStatusAdapter;
    private SwipeRefreshLayout swiperefresh;
    private TextView tvNoResult;
    private RecyclerView rv_fileList;

    public void getPosition(int i) {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {

        View rootView = layoutInflater.inflate(R.layout.gb_fragment_whatsapp_image, viewGroup, false);

        swiperefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        tvNoResult = (TextView) rootView.findViewById(R.id.tvNoResult);
        rv_fileList = (RecyclerView) rootView.findViewById(R.id.rv_fileList);

        initViews();
        setUpAdView();
        return rootView;

    }

    private void setUpAdView() {
        if (getActivity() == null)
            return;
        if (gb_NetworkUtils.isNetworkConnected(getActivity())) {
            gb_AllKeyHub.ShowInterstitialAdsOnCreate(getActivity());
        } else {
            gb_Internet_Alert.alertDialogShow(getActivity(), "" + getString(R.string.gb_network_error));
        }
    }

    private void initViews() {
        this.statusModelArrayList = new ArrayList<>();
        getData();
        this.swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public final void onRefresh() {
                statusModelArrayList = new ArrayList<>();
                getData();
                swiperefresh.setRefreshing(false);
            }
        });
    }

    private void getData() {
        String str = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/.Statuses";
        if (Build.VERSION.SDK_INT >= 29) {
            str = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/media/com.whatsapp/WhatsApp/Media/.Statuses";
        }
        this.allfiles = new File(str).listFiles();
        String str2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Business/Media/.Statuses";
        if (Build.VERSION.SDK_INT >= 29) {
            str2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/.Statuses";
        }
        File[] listFiles = new File(str2).listFiles();
        if (listFiles == null) {
            listFiles = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Business/Media/.Statuses").listFiles();
        }
        try {
            Arrays.sort(this.allfiles, new Comparator() {
                @Override
                public int compare(Object obj, Object obj2) {
                    return WhatsappVideoFragment.getData1(obj, obj2);
                }
            });
            File[] fileArr = this.allfiles;
            for (int i = 0; i < fileArr.length; i++) {
                File file = fileArr[i];
                if (Uri.fromFile(file).toString().endsWith(".mp4")) {
                    this.statusModelArrayList.add(new WhatsappStatusModel("WhatsStatus: " + (i + 1), Uri.fromFile(file), this.allfiles[i].getAbsolutePath(), file.getName()));
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (listFiles != null){
            Arrays.sort(listFiles, new Comparator() {
                @Override
                public int compare(Object obj, Object obj2) {
                    return WhatsappVideoFragment.getData2(obj, obj2);
                }
            });
            for (int i2 = 0; i2 < listFiles.length; i2++) {
                File file2 = listFiles[i2];
                if (Uri.fromFile(file2).toString().endsWith(".mp4")) {
                    this.statusModelArrayList.add(new WhatsappStatusModel("WhatsStatusB: " + (i2 + 1), Uri.fromFile(file2), listFiles[i2].getAbsolutePath(), file2.getName()));
                }
            }
        }
        if (this.statusModelArrayList.size() != 0) {
            this.tvNoResult.setVisibility(View.GONE);
        } else {
            this.tvNoResult.setVisibility(View.VISIBLE);
        }
        this.whatsappStatusAdapter = new WhatsappStatusAdapter(getActivity(), this.statusModelArrayList, this);
        this.rv_fileList.setAdapter(this.whatsappStatusAdapter);
    }

    static int getData1(Object obj, Object obj2) {
        File file = (File) obj;
        File file2 = (File) obj2;
        if (file.lastModified() > file2.lastModified()) {
            return -1;
        }
        return file.lastModified() < file2.lastModified() ? 1 : 0;
    }

    static int getData2(Object obj, Object obj2) {
        File file = (File) obj;
        File file2 = (File) obj2;
        if (file.lastModified() > file2.lastModified()) {
            return -1;
        }
        return file.lastModified() < file2.lastModified() ? 1 : 0;
    }
}
