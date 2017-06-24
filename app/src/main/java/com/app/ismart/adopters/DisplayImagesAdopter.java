package com.app.ismart.adopters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.ismart.R;
import com.app.ismart.app.AppController;
import com.app.ismart.databinding.ImagesItemBinding;
import com.app.ismart.dto.DisplayDto;
import com.app.ismart.rcvbase.BaseRecyclerViewAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Faheem-Abbas on 6/10/2017.
 */

public class DisplayImagesAdopter extends BaseRecyclerViewAdapter<DisplayDto, ImagesItemBinding> {
    DisplayImageOptions options;
    public DisplayImagesAdopter(List<DisplayDto> data, @NotNull Context context) {
        super(data, context);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loadingimage) // resource or drawable
                .showImageForEmptyUri(R.drawable.loadingimage) // resource or drawable
                .showImageOnFail(R.drawable.loadingimage).cacheInMemory(true).cacheOnDisk(true).build();
    }

    @Override
    protected View onCreateViewHolderDynamic(Context context, LayoutInflater inflater, ViewGroup viewGroup, int viewType) {
        return inflater.inflate(R.layout.images_item, null);
    }

    @Override
    protected void onBindViewHolderDynamic(DisplayDto item, ViewHolder viewHolder, int position) {
        viewHolder.binding.txtItemName.setText(item.display);
        if(item.displayimage!=null && !item.displayimage.equals("")){

                AppController.getUniversalImageLoaderInstance().displayImage(context.getString(R.string.server_url)+item.displayimage, viewHolder.binding.imageView3, options);

        }
    }

    @Override
    protected void onViewHolderCreation(View itemView) {

    }

    @Override
    protected void onViewHolderCreated(ViewHolder viewHolder) {

    }
}

