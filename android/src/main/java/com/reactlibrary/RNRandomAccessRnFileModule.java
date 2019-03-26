
package com.reactlibrary;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import java.io.File;
import java.io.IOException;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import java.util.Map;
import java.util.HashMap;

public class RNRandomAccessRnFileModule extends ReactContextBaseJavaModule {
  private static LinkedBlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>(); 
  private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 10, 5000, TimeUnit.MILLISECONDS, taskQueue);

  private final ReactApplicationContext reactContext;

  public RNRandomAccessRnFileModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    
  }

  @Override
  public String getName() {
    return "RNRandomAccessRnFile";
  }

  @ReactMethod
  public void ensureFileExists(final String filePath, final Promise promise) {
    threadPool.execute(new Runnable() {
      @Override
      public void run() {
        try {
          File f = new File(filePath);
          File parentDir = f.getParentFile();
          if (!parentDir.exists()) {
            parentDir.mkdirs();
          }

          if (!f.exists()) {
            f.createNewFile();
          }
        } catch (IOException ioe) {
          promise.reject("unable to create file: " + filePath);
        }
      }
    });
  }
  
  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put("documentPath", this.getReactApplicationContext().getFilesDir().getAbsolutePath());
    constants.put("tempPath", this.getReactApplicationContext().getCacheDir().getAbsolutePath());
    constants.put("cachePath", this.getReactApplicationContext().getCacheDir().getAbsolutePath());
    return constants;
  }
  
}