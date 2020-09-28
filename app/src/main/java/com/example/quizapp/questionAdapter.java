package com.example.quizapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

import static com.example.quizapp.TeacherSubjects.catList;
import static com.example.quizapp.TeacherSubjects.choosedSub;
import static com.example.quizapp.TeacherTopics.topicId;
import static com.example.quizapp.TeacherTopics.topicNo;

public class questionAdapter extends RecyclerView.Adapter<questionAdapter.viewHolder> {

    private List<Questions> quesList;
    public questionAdapter(List<Questions> quesList) {
        this.quesList = quesList;
    }


    @NonNull
    @Override
    public questionAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_btn,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull questionAdapter.viewHolder holder, int position) {
        holder.setData(position,this);
    }


    @Override
    public int getItemCount() {
        return quesList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private ImageView deleteQuiz;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.subjectList);
            deleteQuiz = itemView.findViewById(R.id.delete_btn);
        }

        private void setData(final int position, final questionAdapter quizAdapter){
            title.setText("Question " +  String.valueOf(position+1));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(),TeachQuizAdd.class);
                    intent.putExtra("Action","Edit");
                    intent.putExtra("Q_ID", position);
                    itemView.getContext().startActivity(intent);
                }
            });

            deleteQuiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog dialog = new AlertDialog.Builder(itemView.getContext())
                            .setTitle("Delete Question")
                            .setMessage("Are you sure about deleting this Question?")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    removeQuestion(position, itemView.getContext(), quizAdapter);
                                }
                            })
                            .setNegativeButton("Cancel",null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0,0,50,0);
                    dialog.getButton(dialog.BUTTON_NEGATIVE).setLayoutParams(params);

                }
            });
        }

        private void removeQuestion(final int position, final Context context, final questionAdapter quizAdapter)
        {
            final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

            firestore.collection("QUIZ").document(catList.get(choosedSub).getId())
                    .collection(topicId.get(topicNo)).document(quesList.get(position).getQustID())
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Map<String,Object> quesDoc = new ArrayMap<>();
                            int index=1;
                            for(int i=0; i< quesList.size(); i++)
                            {
                                if(i != position)
                                {
                                    quesDoc.put("Q" + String.valueOf(index) + "_ID", quesList.get(i).getQustID());
                                    index++;
                                }
                            }

                            quesDoc.put("Count", String.valueOf(index - 1));

                            firestore.collection("QUIZ").document(catList.get(choosedSub).getId())
                                    .collection(topicId.get(topicNo)).document("Questions")
                                    .set(quesDoc)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            Toast.makeText(context,"Question was Deleted Successfully", Toast.LENGTH_SHORT).show();

                                            quesList.remove(position);
                                            quizAdapter.notifyDataSetChanged();

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
