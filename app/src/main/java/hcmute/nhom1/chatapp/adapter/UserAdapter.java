package hcmute.nhom1.chatapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hcmute.nhom1.chatapp.Model.Chat;
import hcmute.nhom1.chatapp.Model.ChatList;
import hcmute.nhom1.chatapp.Model.User;
import hcmute.nhom1.chatapp.R;
import hcmute.nhom1.chatapp.UI.InformationUserActivity;
import hcmute.nhom1.chatapp.UI.MainChatAppActivity;
import hcmute.nhom1.chatapp.UI.MessageActivity;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private List<User> listUser;
    private boolean isChat;
    String theLastMessage;
    public UserAdapter(Context context,List<User> mUser,boolean isChat){
        this.context = context;
        this.listUser = mUser;
        this.isChat = isChat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_item,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = listUser.get(position);
        holder.userName.setText(user.getUsername());
        if(user.getImageURL().equals("default")){
            holder.profile_image.setImageResource(R.drawable.image_avatar);
        }
        else{
            Picasso.get().load(user.getImageURL()).into(holder.profile_image);
        }

        if(isChat){
            Log.d("TTT","last");
            lastMessage(user.getId(), holder.last_message,holder.img_newmessage,holder.userName,holder.last_message,holder.textViewTime);
        }
        else{
            holder.last_message.setVisibility(View.GONE);
        }

        if(isChat){
            if(user.getStatus().equals("online")){
                holder.img_onl.setVisibility(View.VISIBLE);
                holder.img_off.setVisibility(View.GONE);
            }
            else{
                holder.img_off.setVisibility(View.VISIBLE);
                holder.img_onl.setVisibility(View.GONE);
            }
        }
        else{
            holder.img_onl.setVisibility(View.GONE);
            holder.img_off.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, MessageActivity.class);
                intent.putExtra("userid",user.getId());
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent =new Intent(context, InformationUserActivity.class);
                intent.putExtra("userid",user.getId());
                context.startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView userName;
        public CircleImageView profile_image;
        private CircleImageView img_off,img_onl,img_newmessage;
        private TextView last_message,textViewTime;
        public ViewHolder(View itemView){
            super(itemView);
            userName = itemView.findViewById(R.id.textViewUserNameItem);
            profile_image = itemView.findViewById(R.id.imageUserItem);
            img_off = itemView.findViewById(R.id.imageViewOffline);
            img_onl = itemView.findViewById(R.id.imageViewOnline);
            last_message = itemView.findViewById(R.id.last_message);
            img_newmessage = itemView.findViewById(R.id.checkNewMessage);
            textViewTime = itemView.findViewById(R.id.textViewHours);
        }
    }

    private void lastMessage(String userid, TextView last_msg,CircleImageView img_checkMessage,TextView username,TextView last_message,TextView time){
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            Log.d("TTT", "1");
            theLastMessage = "default";
            String id;
            final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference reference = FirebaseDatabase.getInstance("https://chatapp-ff2dd-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Chats");
            id = firebaseUser.getUid();
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("TTT", firebaseUser.getUid());
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Chat chat = data.getValue(Chat.class);

                        if ((chat.getReceiver().equals(userid) && chat.getSender().equals(id))) {
                            theLastMessage ="You: " + chat.getMessage();
                        }
                        else if((chat.getReceiver().equals(id) && chat.getSender().equals(userid))){
                            theLastMessage =chat.getMessage();

                            //=======
                            if(!chat.isIsseen()){
                                Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                                img_checkMessage.setVisibility(View.VISIBLE);
                                username.setTypeface(boldTypeface);
                                time.setTypeface(boldTypeface);
                                last_message.setTypeface(boldTypeface);
                            }
                            else{
                                Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.NORMAL);
                                img_checkMessage.setVisibility(View.GONE);
                                username.setTypeface(boldTypeface);
                                time.setTypeface(boldTypeface);
                                last_message.setTypeface(boldTypeface);
                            }

                            //=========

                        }


                    }

                    switch (theLastMessage) {
                        case "default":
                            last_msg.setText("No Message");
                            break;
                        default:
                            last_msg.setText(theLastMessage);
                            break;
                    }
                    theLastMessage = "default";
                    Log.d("TTTC", last_msg.getWidth() +"");
                    last_msg.setMaxWidth(550);





                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            DatabaseReference referencecChatlist = FirebaseDatabase.getInstance("https://chatapp-ff2dd-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Chatlist").child(id);
            referencecChatlist.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        ChatList chat = data.getValue(ChatList.class);
                        if(chat.getId().equals(userid)){
                            time.setText(chat.getTime().substring(11,16));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
   }

}
