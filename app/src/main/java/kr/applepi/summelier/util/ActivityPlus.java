package kr.applepi.summelier.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by KimHeekue on 2014-11-12.
 */
public abstract class ActivityPlus
		extends Activity
{

	public View view_(int id)
	{
		return findViewById(id);
	}

	public void onClick(int id, View.OnClickListener lis)
	{
		view_(id).setOnClickListener(lis);
	}

	public void toast(String text, int duration)
	{
		Toast.makeText(this, text, duration).show();
	}
	public void alert(String title, String message, DialogInterface.OnClickListener lis) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);


		alert.setPositiveButton(android.R.string.ok, lis);
		alert.setTitle(title);
		alert.setMessage(message);
		alert.show();
	}




	public TextView textView_(int id)
	{
		return (TextView)findViewById(id);
	}

	public String text_(int id)
	{
		return textView_(id).getText().toString();
	}

	public void text_(int id, Object text)
	{
		textView_(id).setText(text.toString());
	}

	public void text_f(int id, String format, Object ...args)
	{
		text_(id, String.format(format, args));
	}


	public EditText editText_(int id)
	{
		return (EditText)view_(id);
	}
	public Editable edit_(int id)
	{
		return editText_(id).getText();
	}
	public void edit_(int id, String text)
	{
		editText_(id).setText(text);
	}
}
