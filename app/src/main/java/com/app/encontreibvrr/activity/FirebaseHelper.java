package com.app.encontreibvrr.activity;

/**
 * Created by adria on 30/07/2016.
 */

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * 1.SAVE DATA TO FIREBASE
 * 2. RETRIEVE
 * 3.RETURN AN ARRAYLIST
 */
public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved=null;
    ArrayList<Spacecraft> spacecrafts = new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
        //root = FirebaseDatabase.getInstance().getReference().child(room_name);
    }

    //WRITE IF NOT NULL
    public Boolean save(Spacecraft spacecraft)
    {
        if(spacecraft == null)
        {
            saved=false;
        }

        else {
            try
            {
                db.child("Spacecraft").push().setValue(spacecraft);
                saved = true;

            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }

        return saved;
    }

    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData(DataSnapshot dataSnapshot)
    {
        spacecrafts.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Spacecraft spacecraft = ds.getValue(Spacecraft.class);
            spacecrafts.add(spacecraft);
        }
    }

    //READ THEN RETURN ARRAYLIST
    // Lê em seguida, retornar ArrayList
    public ArrayList<Spacecraft> retrieve() {

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return spacecrafts;
    }
}

