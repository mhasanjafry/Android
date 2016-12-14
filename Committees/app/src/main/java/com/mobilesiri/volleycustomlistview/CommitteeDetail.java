package com.mobilesiri.volleycustomlistview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mobilesiri.model.Committees;

public class CommitteeDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_detail);

        Intent i = getIntent();
        final Committees bill = (Committees) i.getSerializableExtra("bills");

        TextView billidval = (TextView)findViewById(R.id.billidval);
        billidval.setText(((bill.getID()).isEmpty())?"N.A." : bill.getID());

        TextView billid = (TextView)findViewById(R.id.billid);
        billid.setText("Committee ID:");

        TextView titleval = (TextView)findViewById(R.id.titleval);
        titleval.setText(((bill.getname()).isEmpty())?"N.A." : bill.getname());

        TextView title = (TextView)findViewById(R.id.title);
        title.setText("Name:");

        TextView typeval = (TextView)findViewById(R.id.typeval);
        typeval.setText(((bill.getChamber()).isEmpty())?"N.A." : bill.getChamber());

        TextView type = (TextView)findViewById(R.id.type);
        type.setText("Chamber:");

        TextView sponsorval = (TextView)findViewById(R.id.sponsorval);
        sponsorval.setText(((bill.getparent()).isEmpty())?"N.A." : bill.getparent());

        TextView sponsor = (TextView)findViewById(R.id.sponsor);
        sponsor.setText("Parent Committee:");

        TextView Chamber = (TextView)findViewById(R.id.chamber);
        Chamber.setText("Contact:");

        TextView ChamberValue = (TextView)findViewById(R.id.chamberval);
        ChamberValue.setText(((bill.getcontact()).isEmpty())?"N.A." : bill.getcontact());

        TextView status = (TextView)findViewById(R.id.status);
        status.setText("Office:");

        TextView statusval = (TextView)findViewById(R.id.statusval);
        statusval.setText(((bill.getoffice()).isEmpty())?"N.A." : bill.getoffice());
    }
}
