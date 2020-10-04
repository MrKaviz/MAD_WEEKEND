package Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import Models.Category;
import com.example.quizapp.R;
import com.example.quizapp.SplashScreen;

import java.util.List;

import Students.QuestionCate;

public class categoryAdapter extends BaseAdapter {

    private List<Category> catList;

    public categoryAdapter(List<Category> catList) {
        this.catList = catList;
    }

    @Override
    public int getCount() {
        return catList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        View view;
        if(convertView==null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_items,parent,false);
        }
        else{
            view = convertView;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SplashScreen.cateIndex = position;
                Intent intent = new Intent(parent.getContext(), QuestionCate.class);
                parent.getContext().startActivity(intent);
            }
        });

        ((TextView) view.findViewById(R.id.subjectList)).setText(catList.get(position).getName());

        return view;
    }

}
