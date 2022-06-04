package hcmute.nhom1.chatapp.BottomSheet;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hcmute.nhom1.chatapp.Model.Chat;
import hcmute.nhom1.chatapp.R;
import hcmute.nhom1.chatapp.UI.SendAgainActivity;

public class BottomSheetMessage extends BottomSheetDialogFragment {
    String msg;
    String id_chat;
    Chat message;
    ImageView imgCopy,imgDelete,imgShare;
    Task<Void> reference;
    public BottomSheetMessage(String msg,String id,Chat chat){
        this.msg = msg;
        this.id_chat = id;
        this.message = chat;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.bottom_sheet_message,container,false);
        imgCopy = view.findViewById(R.id.imageView8);
        imgDelete = view.findViewById(R.id.imageView12);
        imgShare = view.findViewById(R.id.imageView9);

        imgCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", msg);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Copy", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance("https://chatapp-ff2dd-default-rtdb.asia-southeast1.firebasedatabase.app")
                        .getReference("Chats").child(id_chat).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SendAgainActivity.class);
                intent.putExtra("chat",message);
                startActivity(intent);
                dismiss();
            }
        });


        return view;
    }
}
