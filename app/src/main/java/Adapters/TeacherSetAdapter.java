package Adapters;

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

import com.example.quizapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.List;
import java.util.Map;

import Teachers.TeacherQuiz;
import Teachers.TeacherTopics;

import static Teachers.TeacherSubjects.catList;
import static Teachers.TeacherSubjects.choosedSub;
import static Teachers.TeacherTopics.topicNo;

public class TeacherSetAdapter  extends RecyclerView.Adapter<TeacherSetAdapter.viewHolder> {

    private List<String> topicIds;

    public TeacherSetAdapter(List<String> topicIds) {
        this.topicIds = topicIds;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_btn, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherSetAdapter.viewHolder holder, int position) {
        String setID = topicIds.get(position);
        holder.setData(position,setID,this);
    }

    @Override
    public int getItemCount() {
        return topicIds.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private TextView topicName;
        private ImageView deleteTopBtn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            topicName = itemView.findViewById(R.id.subjectList);
            deleteTopBtn = itemView.findViewById(R.id.delete_btn);

        }

        private void setData(final int position, final String setID, final TeacherSetAdapter topicadapter) {
            topicName.setText("Chapter " + String.valueOf(position + 1));


           itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    topicNo = position;

                    Intent intent = new Intent(itemView.getContext(), TeacherQuiz.class);
                    itemView.getContext().startActivity(intent);
                }
            });

         deleteTopBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog dialog = new AlertDialog.Builder(itemView.getContext())
                            .setTitle("Delete Chapter")
                            .setMessage("Are you sure about deleting this chapter?")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    deleteSet(position,setID,itemView.getContext(),topicadapter);
                                }
                            })
                            .setNegativeButton("Cancel",null)
                            .show();

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0,0,50,0);
                    dialog.getButton(dialog.BUTTON_NEGATIVE).setLayoutParams(params);


                }
            });
        }

        private void deleteSet(final int position, String setID, final Context context, final TeacherSetAdapter topicadapter)
        {

            final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

            firestore.collection("QUIZ").document(catList.get(choosedSub).getId())
                    .collection(setID).get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            WriteBatch batch = firestore.batch();

                            for(QueryDocumentSnapshot doc : queryDocumentSnapshots)
                            {
                                batch.delete(doc.getReference());
                            }

                            batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Map<String, Object> catDoc = new ArrayMap<>();
                                    int index=1;
                                    for(int i=0; i< topicIds.size();  i++)
                                    {
                                        if(i != position)
                                        {
                                            catDoc.put("SET" + String.valueOf(index) + "_ID", topicIds.get(i));
                                            index++;
                                        }
                                    }

                                    catDoc.put("Sets", index-1);

                                    firestore.collection("QUIZ").document(catList.get(choosedSub).getId())
                                            .update(catDoc)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(context,"Chapter was deleted Sucesfully",Toast.LENGTH_SHORT).show();

                                                    TeacherTopics.topicId.remove(position);

                                                    catList.get(choosedSub).setNoOfSets(String.valueOf(TeacherTopics.topicId.size()));

                                                    topicadapter.notifyDataSetChanged();


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
