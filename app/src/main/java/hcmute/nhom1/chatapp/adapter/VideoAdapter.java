package hcmute.nhom1.chatapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.VideoView;

import java.util.List;

import hcmute.nhom1.chatapp.Model.ImageFile;
import hcmute.nhom1.chatapp.R;
import hcmute.nhom1.chatapp.UI.InfoVideoActivity;
import hcmute.nhom1.chatapp.UI.MessageActivity;

public class VideoAdapter extends BaseAdapter {
    private Context context;
    private  int layout;
    private List<String> listVideo;

    public VideoAdapter(Context context, int layout, List<String> listImage) {
        this.context = context;
        this.layout = layout;
        this.listVideo = listImage;
    }
    @Override
    public int getCount() {
        return listVideo.size();
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

        VideoView video;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if(view ==null){
            holder=new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            //Anh xa

            holder.video = view.findViewById(R.id.videoViewItem);

            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        String urlVideo = listVideo.get(i);
        holder.video.setVideoURI(Uri.parse(urlVideo));

        holder.video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, InfoVideoActivity.class);
                intent.putExtra("video",urlVideo);
                intent.putExtra("userid", MessageActivity.userid);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
