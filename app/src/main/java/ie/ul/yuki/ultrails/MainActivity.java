package ie.ul.yuki.ultrails;

import ie.ul.yuki.ularts.R;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {

	WebView mWebView;  

	@SuppressLint("SetJavaScriptEnabled")
	
	protected boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mWebView = (WebView) findViewById(R.id.webView1);
		mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.requestFocus();  
        WebViewClient newClient = new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                mWebView.loadUrl("file:///android_asset/new.html");
    			Toast.makeText(getBaseContext(), "No internet available!", Toast.LENGTH_LONG).show();

            }
        	public boolean shouldOverrideUrlLoading(WebView view, String url) {  
        		if (isNetworkAvailable() == false)
        			Toast.makeText(getBaseContext(), "No internet available!", Toast.LENGTH_LONG).show();
        		else {
            	if(url.contains("google")) {
            		String tmp[] = url.toString().split("/");
            		String urlValue = tmp[tmp.length -1].split("=")[1];

            		String locValue = urlValue.split("\\(")[0];
            		String locName = urlValue.split("\\(")[1].split("\\)")[0];
            		Intent intent = new Intent(Intent.ACTION_VIEW);
            		intent.setData(Uri.parse("geo:?q="+locValue+"("+locName+")&z=15"));
            		startActivity(intent);
            		return true;
            		
            	}
            	else if (url.contains("facebook")) {
            		String tmp[] = url.toString().split("/");
            		String urlValue = tmp[tmp.length -1];
            		 try {
            			 Intent intent = new Intent(Intent.ACTION_VIEW);
            			 intent.setData(Uri.parse("fb://photo/"+urlValue));
            			 startActivity(intent);
            			 } catch (Exception e) {
            				 Intent intent = new Intent(Intent.ACTION_VIEW);
                			 intent.setData(Uri.parse("https://www.facebook.com/"+urlValue));
                			 startActivity(intent);
            			   }
            		return true;
            	}
            	else {
            		return false;  
            	}}
        		return true;
            }  
     
        };  
		mWebView.setWebViewClient(newClient);
		mWebView.setWebChromeClient(new WebChromeClient() {
			 public void onGeolocationPermissionsShowPrompt(String origin, android.webkit.GeolocationPermissions.Callback callback) {
			    callback.invoke(origin, true, false);
			 }
			});
		mWebView.loadUrl("http://skynet.ie/~yuki/app/index1.html");
		
	}
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {  
            mWebView.goBack();  
            return true;  
        }  
        return false;  
    }  
    
   

}
