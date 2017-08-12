package com.app.ismart.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.app.ismart.R;
import com.app.ismart.async.AsyncDispatcher;
import com.app.ismart.async.IAsync;
import com.app.ismart.databinding.FragmentcheckingBinding;
import com.app.ismart.dto.DisplayDto;
import com.app.ismart.dto.ItemDto;
import com.app.ismart.dto.ShopDto;
import com.app.ismart.dto.ShopImagesDto;
import com.app.ismart.realm.RealmController;
import com.app.ismart.realm.repository.ShopImagesRepository;
import com.app.ismart.realm.specfication.GetAllData;
import com.app.ismart.utils.FragmentUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by HP 2 on 4/23/2017.
 */

public class FragmentChecking extends Fragment implements View.OnClickListener {
    public ShopDto shopDto;
    FragmentcheckingBinding layoutBinding;
    Context context;
    private int REQUEST_CAMERA = 12032, SELECT_FILE = 10923;
    private boolean isCamerSelected = false;
    boolean isbefore = false;
    Uri beforeImageURI;
    Uri afterImageURI;
    Uri imageToUploadUri;
    ShopImagesRepository shopImagesRepository;
    private RealmController realmController;
    String date;
    String afterImage, beforeImage;
    public String display;
    public int pos;
    public int i;
    public String a;
    public List<ItemDto> item;
    public ArrayList<DisplayDto> plano;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutBinding = DataBindingUtil.inflate(inflater, R.layout.fragmentchecking, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(layoutBinding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(shopDto.getName());
        layoutBinding.toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        layoutBinding.btnAfter.setOnClickListener(this);
        layoutBinding.btnBefore.setOnClickListener(this);
        layoutBinding.btnSave.setOnClickListener(this);
        context = getContext();
        realmController = RealmController.with(this);
        shopImagesRepository = new ShopImagesRepository(realmController.getRealm());
        date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
      //  i= Integer.parseInt(pos);
        a =display;
       // a =item.get(0).getTitle();
      //  Toast.makeText((Activity)context,""+a,Toast.LENGTH_LONG).show();

        List<ShopImagesDto> shopimages = shopImagesRepository.queryForSpecficDate(new GetAllData(), date, "" + shopDto.getId(),""+shopDto.getVisitId(),a);
        if (shopimages.size() >= 1) {
        //    Toast.makeText((Activity)context,"data exists",Toast.LENGTH_LONG).show();
            afterImage = shopimages.get(0).afterImage;
            beforeImage = shopimages.get(0).beforeImage;
        }
        new AsyncDispatcher(new IAsync() {
            Bitmap before, after;

            @Override
            public void IOnPreExecute() {

            }

            @Override
            public Object IdoInBackGround(Object... params) {
                if (beforeImage != null) {
                    before = Base64ToBitmap(beforeImage);
                }
                if (afterImage != null) {
                    after = Base64ToBitmap(afterImage);
                }
                return null;
            }

            @Override
            public void IOnPostExecute(Object result) {
                if (before != null) {
                    layoutBinding.imgBefore.setImageBitmap(before);
                }
                if (after != null) {
                    layoutBinding.imgAfter.setImageBitmap(after);
                }
            }
        });

        return layoutBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBefore:
                isbefore = true;
                //selectImage();
                //  cameraIntent();
                boolean camerResult = checkCameraPermission(context);
                boolean externaStorageResult = checkExternalStoragePermission(context);
                if (externaStorageResult && camerResult) {
                    isCamerSelected = true;
                    cameraIntent();
                }
                break;
            case R.id.btnAfter:
                isbefore = false;
                // selectImage();
                //  cameraIntent();
                boolean camerResult1 = checkCameraPermission(context);
                boolean externaStorageResult1 = checkExternalStoragePermission(context);
                if (externaStorageResult1 && camerResult1) {
                    isCamerSelected = true;
                    cameraIntent();
                }
                break;
            case R.id.btnSave:


           /*     if (beforeImageURI == null && beforeImage==null) {
                    Toast.makeText(context, "please take before photo", Toast.LENGTH_SHORT).show();
                } else if (afterImageURI == null && afterImage==null) {
                    Toast.makeText(context, "please take after photo", Toast.LENGTH_SHORT).show();
                } else { */
                    new AsyncDispatcher(new IAsync() {


                        @Override
                        public void IOnPreExecute() {

                        }

                        @Override
                        public Object IdoInBackGround(Object... params) {
                            // get the base 64 string
                            if(beforeImageURI!=null) {
                                Bitmap before = getBitmap(beforeImageURI);
                                beforeImage = Base64.encodeToString(getBytesFromBitmap(before),
                                        Base64.NO_WRAP);
                            }
                            if(afterImageURI!=null) {
                                Bitmap after = getBitmap(afterImageURI);
                                afterImage = Base64.encodeToString(getBytesFromBitmap(after),
                                        Base64.NO_WRAP);
                            }else{
                               // Bitmap after = getBitmap(afterImageURI);
                                Bitmap after = BitmapFactory.decodeResource(context.getResources(), R.drawable.noimg);
                                afterImage = Base64.encodeToString(getBytesFromBitmap(after),
                                        Base64.NO_WRAP);
                            }
                            return null;
                        }

                        @Override
                        public void IOnPostExecute(Object result) {


                            ShopImagesDto dto = new ShopImagesDto();
                            dto.shopid = "" + shopDto.getId();
                            dto.beforeImage = beforeImage;
                            dto.afterImage = afterImage;
                            dto.date = date;
                            dto.categoryName = a;
                            dto.visitid = "" + shopDto.getVisitId();
                            try {
                                List<ShopImagesDto> shopimages = shopImagesRepository.queryForSpecficDate(new GetAllData(), date, "" + shopDto.getId(), "" + shopDto.getVisitId(), a);
                                if (shopimages.size() >= 1) {
                                    dto.id = shopimages.get(0).id;
                                    shopImagesRepository.update(dto);
                                } else {

                                    shopImagesRepository.add(dto);
                                }
                                Toast.makeText(context, "Before Photo Saved", Toast.LENGTH_SHORT).show();



                              //  getActivity().getSupportFragmentManager().popBackStack();
                            } catch (Exception e) {


                            }

                            FragmentTakeQuantity fragment = new FragmentTakeQuantity();
                            fragment.item = item;
                            fragment.shopDto = shopDto;
                            fragment.display=display;
                            fragment.displaylist =plano;
                            new FragmentUtils(getActivity(), fragment, R.id.fragContainer);
                        }
                    });

              //  }

                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from gallery",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Take Photo")) {
//                    userChoosenTask ="Take Photo";
                    boolean camerResult = checkCameraPermission(context);
                    boolean externaStorageResult = checkExternalStoragePermission(context);
                    if (externaStorageResult && camerResult) {
                        isCamerSelected = true;
                        cameraIntent();
                    }

                } else if (items[item].equals("Choose from gallery")) {
//                     / ="Choose from Library";
                    boolean externaStorageResult = checkExternalStoragePermission(context);
                    if (externaStorageResult) {
                        isCamerSelected = false;
                        galleryIntent();
                    }


                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select file"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File newFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".png");
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String ext = newFile.getName().substring(newFile.getName().lastIndexOf(".") + 1);
        String type = mime.getMimeTypeFromExtension(ext);
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                imageToUploadUri = FileProvider.getUriForFile(getContext(), "com.app.ismart.fileProvider", newFile);
                intent.setDataAndType(imageToUploadUri, type);
            } else {
                imageToUploadUri = Uri.fromFile(newFile);

            }
            chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageToUploadUri);
            startActivityForResult(chooserIntent, REQUEST_CAMERA);
        } catch (ActivityNotFoundException anfe) {

            //Toast.makeText(getContext(), R.string.cant_camera, Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        if (imageToUploadUri != null) {
            Uri selectedImage = imageToUploadUri;
            getContext().getContentResolver().notifyChange(selectedImage, null);

            Bitmap reducedSizeBitmap = getBitmap(imageToUploadUri);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            File destination = null;
            destination = new File(String.valueOf(imageToUploadUri));

            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();

            }
           // Uri tempUri = getImageUri(getContext(), reducedSizeBitmap);
            if (isbefore) {
                beforeImageURI = imageToUploadUri;
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), beforeImageURI);
                    // Log.d(TAG, String.valueOf(bitmap));
                    layoutBinding.imgBefore.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                afterImageURI = imageToUploadUri;
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), afterImageURI);
                    // Log.d(TAG, String.valueOf(bitmap));
                    layoutBinding.imgAfter.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private Bitmap getBitmap(Uri path) {


        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
            in = getContext().getContentResolver().openInputStream(path);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();


            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }


            Bitmap b = null;
            in = getContext().getContentResolver().openInputStream(path);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();


                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                b.recycle();
                b = scaledBitmap;

                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();


            return b;
        } catch (IOException e) {

            return null;
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        if (isbefore) {
            beforeImageURI = data.getData();
        } else {
            afterImageURI = data.getData();
        }


    }


    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 124;

    public boolean checkCameraPermission(final Context context) {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the User's response! After the User
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                requestPermissions(
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);


            } else {
                // No explanation needed, we can request the permission.
                requestPermissions(
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean checkExternalStoragePermission(final Context context) {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the User's response! After the User
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);


            } else {
                // No explanation needed, we can request the permission.
                requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)

    {
        switch (requestCode) {


            case MY_PERMISSIONS_REQUEST_CAMERA:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {
                        isCamerSelected = true;
                        boolean externaStorageResult = checkExternalStoragePermission(context);
                        if (externaStorageResult) {
                            cameraIntent();
                        }


                    }
                } else {

                }


                break;
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (isCamerSelected) {
                            cameraIntent();
                        } else {
                            isCamerSelected = false;
                            galleryIntent();
                        }


                    }
                } else {

                }


                break;

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    // convert from bitmap to byte array
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    Bitmap Base64ToBitmap(String myImageData) {
        byte[] imageAsBytes = Base64.decode(myImageData.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}
