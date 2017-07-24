package com.app.ismart.fragments;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.app.ismart.MainActivity;
import com.app.ismart.R;
import com.app.ismart.adopters.PopSubmitAdopter;
import com.app.ismart.api.IApiCalls;
import com.app.ismart.databinding.FragmentPopsubmitBinding;
import com.app.ismart.dto.Pop;
import com.app.ismart.dto.PopSubmitDto;
import com.app.ismart.dto.ShopDto;
import com.app.ismart.interfaces.IonTakePhoto;
import com.app.ismart.interfaces.IonUpdateMark;
import com.app.ismart.interfaces.OnEditTextChanged;
import com.app.ismart.rcvbase.RecyclerViewUtils;
import com.app.ismart.realm.RealmController;
import com.app.ismart.realm.repository.PopRepository;
import com.app.ismart.realm.repository.PopSubmitRepository;
import com.app.ismart.realm.specfication.GetAllData;
import com.app.ismart.rest.APIError;
import com.app.ismart.rest.IRestResponseListner;
import com.app.ismart.restmanagers.PopManger;
import com.app.ismart.retrofit.RetrofitClient;
import com.app.ismart.utils.InternetConnection;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Faheem-Abbas on 5/29/2017.
 */

public class FragmentPopSubmit extends Fragment implements IRestResponseListner<List<Pop>>, OnEditTextChanged, IonTakePhoto {
    FragmentPopsubmitBinding layoutBinding;
    private RealmController realmController;
    PopRepository repository;
    PopSubmitRepository popSubmitRepository;
    List<Pop> pops;
    public ShopDto shopDto;
    String date;
    Uri imageToUploadUri;
    private int REQUEST_CAMERA = 12032, SELECT_FILE = 10923;
    int position = 0;
    public IonUpdateMark ionUpdateMark;
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(ionUpdateMark!=null){
            ionUpdateMark.updateui();
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_popsubmit, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(layoutBinding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Pops");
        layoutBinding.toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        realmController = RealmController.with(this);
        repository = new PopRepository(realmController.getRealm());
        popSubmitRepository = new PopSubmitRepository(realmController.getRealm());
        pops = repository.query(new GetAllData());
        if (pops.size() >= 1) {

            setDataAdopter(pops);

        } else {
            if (InternetConnection.checkConnection(getContext())) {
                IApiCalls api = RetrofitClient.instance.retrofit.create(IApiCalls.class);
                Call<List<Pop>> apiCall = api.getpops();
                apiCall.enqueue(new PopManger(this));
            } else {


                Toast.makeText(getContext(), "No internet available", Toast.LENGTH_SHORT).show();


            }
        }
        layoutBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = ((MainActivity) getActivity()).checklocation();
                if (location != null) {
                    for (Pop pop : pops) {
                        if (!pop.getQuantity().equals("0") && pop.getImage() != null) {
                            PopSubmitDto dto = new PopSubmitDto();
                            dto.timestamp = getDateTime();
                            dto.date = date;
                            dto.popid = pop.getId() + "";
                            dto.shopid = shopDto.getId() + "";
                            dto.quantity = pop.getQuantity();
                            dto.photo = pop.getImage();
                            dto.location=location;
                            dto.visitid = shopDto.getVisitId() + "";
                            List<PopSubmitDto> exists = popSubmitRepository.query(new GetAllData(), dto.popid, dto.shopid,dto.visitid);
                            if (exists.size() >= 1) {
                                popSubmitRepository.update(dto);
                            } else {
                                popSubmitRepository.add(dto);
                            }

                        }

                    }
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
        return layoutBinding.getRoot();
    }

    private void setDataAdopter(List<Pop> data) {
        for (int i = 0; i < pops.size(); i++) {
            List<PopSubmitDto> exists = popSubmitRepository.query(new GetAllData(), pops.get(i).getId() + "", shopDto.getId() + "",shopDto.getVisitId()+"");
            if (exists.size() >= 1) {
                pops.get(i).setQuantity(exists.get(0).quantity);
                pops.get(i).setImage(exists.get(0).photo);
            }

        }


        PopSubmitAdopter adapter = new PopSubmitAdopter(data, getActivity(), this,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutBinding.rcvpop.setLayoutManager(RecyclerViewUtils.getGridLayoutManager(getContext(), 1));
        layoutBinding.rcvpop.setItemAnimator(new DefaultItemAnimator());
        layoutBinding.rcvpop.setAdapter(adapter);
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
        layoutBinding.rcvpop.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getContext()).paint(paint).build());
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onSuccessResponse(List<Pop> model) {
        repository.removeAll();
        repository.add(model);
        pops = model;
        setDataAdopter(model);
    }

    @Override
    public void onErrorResponse(APIError erroModel) {

        if (erroModel != null) {
            Log.d("Api call failure", erroModel.message);
        }
        pops = repository.query(new GetAllData());
        setDataAdopter(pops);
    }

    @Override
    public void onTextChanged(int position, String charSeq) {
        pops.get(position).setQuantity(charSeq);
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
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

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageToUploadUri);
                // Log.d(TAG, String.valueOf(bitmap));
                String base64 = Base64.encodeToString(getBytesFromBitmap(bitmap),
                        Base64.NO_WRAP);
                pops.get(position).setImage(base64);
            } catch (IOException e) {
                e.printStackTrace();
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

                        boolean externaStorageResult = checkExternalStoragePermission(getContext());
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

                        cameraIntent();


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

    @Override
    public void onTakePhoto(int position) {
        this.position = position;
        boolean camerResult = checkCameraPermission(getContext());
        boolean externaStorageResult = checkExternalStoragePermission(getContext());
        if (externaStorageResult && camerResult) {

            cameraIntent();
        }
    }
}
