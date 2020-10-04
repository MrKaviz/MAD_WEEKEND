package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import Models.NewsModel;
import com.example.quizapp.R;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<NewsModel> newsModels;
    private Context mCtx;

    public StudentAdapter(Context mCtx, List<NewsModel> newsModels) {
        this.mCtx = mCtx;
        this.newsModels = newsModels;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.news, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        NewsModel news = newsModels.get(position);

        holder.textViewName.setText(news.getName());
        holder.textViewBrand.setText(news.getBrand());
    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewName, textViewBrand;

        public StudentViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textview_name);
            textViewBrand = itemView.findViewById(R.id.textview_brand);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
