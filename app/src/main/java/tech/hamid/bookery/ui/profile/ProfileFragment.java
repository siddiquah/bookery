package tech.hamid.bookery.ui.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import tech.hamid.bookery.LoginActivity;
import tech.hamid.bookery.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    TextView emailField;
    FirebaseAuth fAuth;
    FirebaseUser user;
    //FloatingActionButton fab;
    String userId;
    //Button resetPassLocal;

    Button resendCode;
    StorageReference storageReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final Button btn = root.findViewById(R.id.button);
        //fab = root.findViewById(R.id.addStoryBtn);
        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        btn.setOnClickListener(v -> logout());
        //fab.setOnClickListener(v -> gotoAddStory());
        emailField = root.findViewById(R.id.email);
        storageReference = FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        if(user != null) {
            emailField.setText(user.getEmail());
        } else {
            Toast.makeText(getActivity(), "We have to fix this.", Toast.LENGTH_LONG).show();
        }
//
//        resetPassLocal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final EditText resetPassword = new EditText(v.getContext());
//                if(!user.isEmailVerified()){
//                    verifyMsg.setVisibility(View.VISIBLE);
//                    resendCode.setVisibility(View.VISIBLE);
//
//                    resetPassLocal.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            final EditText resetPassword = new EditText(v.getContext());
//
//                            final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
//                            passwordResetDialog.setTitle("Reset Password ?");
//                            passwordResetDialog.setMessage("Enter New Password > 6 Characters long.");
//                            passwordResetDialog.setView(resetPassword);
//
//                            passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // extract the email and send reset link
//                                    String newPassword = resetPassword.getText().toString();
//                                    user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            Toast.makeText(getActivity(), "Password Reset Successfully.", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Toast.makeText(getActivity(), "Password Reset Failed.", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                }
//                            });
//
//                            passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // close
//                                }
//                            });
//
//                            passwordResetDialog.create().show();
//
//                        }
//                    });
//                }
//            }
//        });
        return root;
    }
//
//    public void gotoAddStory() {
//        FragmentActivity activity = getActivity();
//        Intent intent = new Intent(activity, AddStoryActivity.class);
//        startActivity(intent);
//    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        FragmentActivity activity = getActivity();
        Intent intent = new Intent(activity, LoginActivity.class);
        startActivity(intent);
        activity.finishAffinity();
    }


}