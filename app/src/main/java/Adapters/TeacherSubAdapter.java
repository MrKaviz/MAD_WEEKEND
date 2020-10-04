package Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import Models.Category;
import com.example.quizapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

import Teachers.TeacherSubjects;
import Teachers.TeacherTopics;

public class TeacherSubAdapter extends RecyclerView.Adapter<TeacherSubAdapter.viewholder> {

    private List<Category> cat_list;

    public TeacherSubAdapter(List<Category> cat_list) {
        this.cat_list = cat_list;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_btn,viewGroup,false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherSubAdapter.viewholder viewholder, int position) {
        String title = cat_list.get(position).getName();

        viewholder.setData(title, position, this);

    }

    @Override
    public int getItemCount() {
        return cat_list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        private TextView catName;
        private ImageView deleteBtn;
        private Dialog editSubject;
        private EditText editSub;
        private Button updateSub;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            catName = itemView.findViewById(R.id.subjectList);
            deleteBtn = itemView.findViewById(R.id.delete_btn);

            editSubject = new Dialog(itemView.getContext());
            editSubject.setContentView(R.layout.edit_subject);
            editSubject.setCancelable(true);
            editSubject.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

            editSub = editSubject.findViewById(R.id.editSubTXT);
            updateSub = editSubject.findViewById(R.id.editSubBTN);

        }

        private void setData(String title, final int position, final TeacherSubAdapter adapter) {
            catName.setText(title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TeacherSubjects.choosedSub=position;
                    Intent intent = new Intent(itemView.getContext(), TeacherTopics.class);
                    itemView.getContext().startActivity(intent);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    editSub.setText(cat_list.get(position).getName());
                    editSubject.show();

                    return false;
                }
            });

            updateSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(editSub.getText().toString().isEmpty())
                    {
                        editSub.setError("Insert Subject Name");
                        return;
                    }

                    updateSubject(editSub.getText().toString(), position, itemView.getContext(), adapter);

                }
            });

           deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alertDialog = new AlertDialog.Builder(itemView.getContext())
                            .setTitle("Delete Category")
                            .setMessage("Are you sure to delete this subject?")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteSubject(position,itemView.getContext(),adapter);
                                }
                            }).setNegativeButton("Cancel",null)
                            .show();

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0,0,50,0);
                }
            });

        }

        private void deleteSubject(final int id, final Context context, final TeacherSubAdapter adapter) {
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();

            Map<String,Object> catDoc = new ArrayMap<>();
            int index=1;
            for(int i=0; i < cat_list.size(); i++)
            {
                if( i != id)
                {
                    catDoc.put("CAT" + String.valueOf(index) + "_ID", cat_list.get(i).getId());
                    catDoc.put("CAT" + String.valueOf(index) + "_NAME", cat_list.get(i).getName());
                    index++;
                }

            }

            catDoc.put("Count", index - 1);

            firestore.collection("QUIZ").document("Categories")
                    .set(catDoc)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(context,"Subject was deleted successfully",Toast.LENGTH_SHORT).show();

                            TeacherSubjects.catList.remove(id);

                            adapter.notifyDataSetChanged();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });


        }

        private void updateSubject(final String newSubName, final int position, final Context context, final TeacherSubAdapter adapter)
        {
            editSubject.dismiss();

            Map<String,Object> catData = new ArrayMap<>();
            catData.put("Name",newSubName);

            final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

            firestore.collection("QUIZ").document(cat_list.get(position).getId())
                    .update(catData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Map<String,Object> catDoc = new ArrayMap<>();
                            catDoc.put("CAT" + String.valueOf(position + 1) + "_NAME",newSubName);

                            firestore.collection("QUIZ").document("Categories")
                                    .update(catDoc)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            Toast.makeText(context,"Subject was Changed Successfully",Toast.LENGTH_SHORT).show();
                                            TeacherSubjects.catList.get(position).setName(newSubName);
                                            adapter.notifyDataSetChanged();


                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });


        }

}


}
