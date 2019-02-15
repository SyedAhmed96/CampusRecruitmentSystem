package com.example.ahmed.campusrecruitmentsystem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends StudentBaseFragment {

    public static final int RC_PHOTO_PICKER = 2;
    private static final String TAG = "check";
    EditText editTextName, editTextPassword, editTextEmailAddress, editTextCGPA;
    Button Btn_edit, Btn_updateProfile;
    ImageView photoImageView;

    //defining firebaseauth reference
    private FirebaseAuth firebaseAuth;

    //Firebase storage references
    private FirebaseDatabase mFirebasedatabase;
    private DatabaseReference mStudentDatabaseReference;
    private ChildEventListener mStudentChildEventListener;

    //Testing
    private DatabaseReference mDatabaseReference;

    //Assistant //Storage
    private StorageReference storageReference;
    private FirebaseStorage mFirebaseStorage;

    Uri downloadUrl;
    ArrayList<Student> profileList;
    String sID;

    myFBDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment__student_profile, container, false);


        //Initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        mFirebaseStorage = FirebaseStorage.getInstance();

        storageReference = mFirebaseStorage.getReference().child("profile_photos");

//        //My Class testing
//        database = new myFBDatabase();

        sID = firebaseAuth.getCurrentUser().getUid();

        //Initialize Firebase database components
        mFirebasedatabase = FirebaseDatabase.getInstance();
        mStudentDatabaseReference = mFirebasedatabase.getReference().child("CampusRecruitmentSystem").child("Students").child("" + firebaseAuth.getCurrentUser().getUid());
        //Testing
        mDatabaseReference = mFirebasedatabase.getReference().child("CampusRecruitmentSystem").child("Students");


        editTextName = v.findViewById(R.id.nameStudentEditText);
        editTextPassword = v.findViewById(R.id.passwordStudentEditText);
        editTextEmailAddress = v.findViewById(R.id.emailAddressStudentEditText);
        editTextCGPA = v.findViewById(R.id.studentGpaEditText);

        profileList = new ArrayList<Student>();

        photoImageView = v.findViewById(R.id.photoImageView);

        //Deactivating Views
        deActivateViews();

        //Custom Adapter

//        ListView listView = (ListView) v.findViewById(R.id.customList);
//        StudentCustomAdapter customAdapter = new StudentCustomAdapter(getActivity().getBaseContext(),contents);
//        listView.setAdapter(customAdapter);


        //Image Picker
        photoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fire an intent to show an image picker
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                // intent.setType("image/jpeg");
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                // intent.setAction(Intent.ACTION_GET_CONTENT);
                // startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
                startActivityForResult(intent, RC_PHOTO_PICKER);
            }
        });


        //Update Profile
        Btn_updateProfile = v.findViewById(R.id.studentUpdateProfileButton);

        Btn_edit = v.findViewById(R.id.studentEditButton);

        downloadUrl = null;
        //Image Picker
        Btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Here have to enable for edittexts
                activateViews();
//
//                // TODO: Fire an intent to show an image picker
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                // intent.setType("image/jpeg");
//                intent.setType("image/*");
//                intent.putExtra(intent.EXTRA_LOCAL_ONLY, true);
//                // intent.setAction(Intent.ACTION_GET_CONTENT);
//                // startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
//                startActivityForResult(intent, RC_PHOTO_PICKER);

            }
        });

        Btn_updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student;

                String name = editTextName.getText().toString();
                String password = editTextPassword.getText().toString();
                String email = editTextEmailAddress.getText().toString();
                String CGPA = editTextCGPA.getText().toString();

                //Student object not right in firebase..
                student = new Student(email, name, password, CGPA, sID);

                //Adding Code for Photo
                if (downloadUrl != null) {
                    String photoURL = downloadUrl.toString();
//                    Toast.makeText(getActivity().getBaseContext(), "if block executed", Toast.LENGTH_SHORT).show();
                    student = new Student(email, name, password, CGPA, photoURL, sID);
                }

                myFBDatabase database = new myFBDatabase();
                database.updateStudentProfile(sID, student);

//                    mStudentDatabaseReference.setValue(student);

//                catch (NullPointerException e){
//                    Toast.makeText(getActivity().getBaseContext(),"Please Enter Some text",Toast.LENGTH_SHORT).show();
//                }

                Toast.makeText(getActivity().getBaseContext(), "Profile Updated..", Toast.LENGTH_SHORT).show();

            }
        });

        return v;
    }


    //Storing Images
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_PHOTO_PICKER) {
            Uri selectedImageUri = data.getData();

            //Get a reference to store file at chat_photos<FILENAME>
            StorageReference photoRef = storageReference.child(selectedImageUri.getLastPathSegment());

            try {
                //Upload File to firebase storge //'this' exception == getActivity()
                photoRef.putFile(selectedImageUri).addOnSuccessListener(
                        getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //Updating download URL(null)
                                downloadUrl = taskSnapshot.getDownloadUrl();
                          //      Toast.makeText(getActivity().getBaseContext(), "downloadedURL:" + downloadUrl.toString(), Toast.LENGTH_SHORT).show();

                                Glide.with(photoImageView.getContext())
                                        .load(downloadUrl.toString())
                                        .into(photoImageView);
                            }
                        });
            } catch (ClassCastException e) {
                Toast.makeText(getActivity().getBaseContext(), "ClassCastExceptionCatched", Toast.LENGTH_SHORT).show();
            }

        }
    }

    //Here have to setEditable false for edittext
    private void deActivateViews() {
        editTextName.setEnabled(false);
        editTextPassword.setEnabled(false);
        editTextEmailAddress.setEnabled(false);
        editTextCGPA.setEnabled(false);
        photoImageView.setEnabled(false);
        // putData();
    }

    //Here have to setEditable true for edittext
    private void activateViews() {
        editTextName.setEnabled(true);
        editTextPassword.setEnabled(true);
        editTextEmailAddress.setEnabled(true);
        editTextCGPA.setEnabled(true);
        photoImageView.setEnabled(true);
    }


}
