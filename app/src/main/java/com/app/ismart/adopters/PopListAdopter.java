package com.app.ismart.adopters;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ismart.R;
import com.app.ismart.api.IApiCalls;
import com.app.ismart.databinding.PopItemBinding;
import com.app.ismart.dto.Pop;
import com.app.ismart.dto.Poprequest;
import com.app.ismart.dto.Response;
import com.app.ismart.dto.UserModel;
import com.app.ismart.rcvbase.BaseRecyclerViewAdapter;
import com.app.ismart.rcvbase.IOnItemClickListner;
import com.app.ismart.rest.APIError;
import com.app.ismart.rest.IRestResponseListner;
import com.app.ismart.restmanagers.UploadPopManger;
import com.app.ismart.retrofit.RetrofitClient;
import com.app.ismart.utils.InternetConnection;
import com.app.ismart.utils.SessionManager;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Faheem-Abbas on 5/21/2017.
 */

public class PopListAdopter extends BaseRecyclerViewAdapter<Pop, PopItemBinding> implements IOnItemClickListner<Pop> {
    Context context;

    public PopListAdopter(List<Pop> data, @NotNull Context context) {

        super(data, context);
        this.context = context;
    }

    @Override
    public void onRecyclerItemClick(Pop model, View view, int position) {
        takeQuantity(model);

    }

    @Override
    protected View onCreateViewHolderDynamic(Context context, LayoutInflater inflater, ViewGroup viewGroup, int viewType) {
        return inflater.inflate(R.layout.pop_item, null);
    }

    @Override
    protected void onBindViewHolderDynamic(Pop item, ViewHolder viewHolder, int position) {
        viewHolder.binding.txtpopname.setText(item.getName());
        setOnItemClickListner(this);
    }

    @Override
    protected void onViewHolderCreation(View itemView) {

    }

    @Override
    protected void onViewHolderCreated(ViewHolder viewHolder) {

    }

    public void takeQuantity(final Pop item) {

        final ProgressDialog progressdialog = new ProgressDialog(context);
        progressdialog.setMessage("Uploading please wait....");

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.quantity_dialog, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);
        userInput.setHint("Enter quantity");
        final TextView title = (TextView) promptsView
                .findViewById(R.id.textView1);
        title.setText("Enter pop quantity");
        Button submit = (Button) promptsView
                .findViewById(R.id.btnSubmit);
        final Button cancel = (Button) promptsView
                .findViewById(R.id.btnCancel);


        // create alert progressdialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = userInput.getText().toString();
                if (!quantity.equalsIgnoreCase("")) {

                    if (InternetConnection.checkConnection(context)) {
                        progressdialog.show();
                        Poprequest poprequest=new Poprequest();
                        poprequest.setItemId(item.getId()+"");
                        poprequest.setQuantity(quantity);
                       UserModel userModel = new SessionManager(context).getSession();
                        IApiCalls api = RetrofitClient.instance.retrofit.create(IApiCalls.class);
                        Call<Response> apiCall = api.uploadPopQuanity(""+item.getId(),quantity,""+userModel.getUseId());
                        apiCall.enqueue(new UploadPopManger(new IRestResponseListner<Response>() {
                            @Override
                            public void onSuccessResponse(Response model) {
                                Log.d("upload data of", "" + item.getId());
                                progressdialog.dismiss();
                                Toast.makeText(context, "Pop quantity uploaded", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onErrorResponse(APIError erroModel) {


                                progressdialog.dismiss();
                                Toast.makeText(context, "Data uploading failure,please try again", Toast.LENGTH_SHORT).show();

                            }
                        }));
                    } else {
                        Toast.makeText(context, "No internet available", Toast.LENGTH_SHORT).show();

                    }


                    alertDialog.cancel();


                } else {
                    Toast.makeText(context, "Please enter quantity", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        // set progressdialog message


    }
}
