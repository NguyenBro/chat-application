package hcmute.nhom1.chatapp.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hcmute.nhom1.chatapp.Model.User;
import hcmute.nhom1.chatapp.R;
import hcmute.nhom1.chatapp.UI.SendAgainActivity;

public class ContactsAdapter extends BaseAdapter {
    private Context context;
    private  int layout;
    private List<User> listUser;
    public String userid;

    public ContactsAdapter(Context context, int layout, List<User> listImage) {
        this.context = context;
        this.layout = layout;
        this.listUser = listImage;
    }
    @Override
    public int getCount() {
        return listUser.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{

        TextView txtName,txtSend,txtPhone;
        CircleImageView imgAvatar;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view ==null){
            holder=new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            //Anh xa

            holder.txtName = view.findViewById(R.id.textViewUserNameItem_Again);
            holder.txtSend = view.findViewById(R.id.textView8);
            holder.imgAvatar = view.findViewById(R.id.imageUserItem_Again);
            holder.txtPhone =view.findViewById(R.id.textView9);

            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();

        }

        User user =listUser.get(i);
        holder.txtSend.setVisibility(View.GONE);
        holder.txtPhone.setVisibility(View.VISIBLE);
        holder.txtName.setText(user.getUsername());
        holder.txtPhone.setText(user.getPhone());
        if(user.getImageURL().equals("default")){
            holder.imgAvatar.setImageResource(R.drawable.image_avatar);
        }
        else{
            Picasso.get().load(user.getImageURL()).into(holder.imgAvatar);
        }

        //userid = user.getId();


        return view;
    }
}
