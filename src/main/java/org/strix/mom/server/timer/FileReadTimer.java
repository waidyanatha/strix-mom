package org.strix.mom.server.timer;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Timer;
import java.util.TimerTask;

import org.strix.mom.server.message.file.FileHandler;
import org.strix.mom.server.message.file.FileListener;

/**
 * Author: Tharindu Jayasuriya
 */
public class FileReadTimer {
	Timer timer;
	private int frequency = 10;
	boolean running = true;
	private FileHandler fileHandler;
	private WatchService watcher;
	private Path dir;
	private FileListener fileListener;

	public FileReadTimer() {
		
		
	}
	
	public void init(FileListener fileListener){
		this.fileListener = fileListener;
		try {
			watcher = FileSystems.getDefault().newWatchService();
		    dir = Paths.get(fileHandler.getInputLocation());
			    WatchKey key = dir.register(watcher,
			    		StandardWatchEventKinds.ENTRY_CREATE
			    		//,StandardWatchEventKinds.ENTRY_DELETE
			    		//,StandardWatchEventKinds.ENTRY_MODIFY
			    		);
		} catch (IOException e) {
			e.printStackTrace();
		}
		timer = new Timer();
		timer.schedule(new RemindTask(), 0, // initial delay
				1 * frequency); // subsequent rate
		//new RemindTask().run();
	}

	class RemindTask extends TimerTask {
		
		public void run() {
			if (running) {
				System.out.format("Timer is up!%n");
				WatchKey key;
			    try {
			        key = watcher.take();
			    } catch (InterruptedException x) {
			    	System.err.println(x);
			        return;
			    }

			    for (WatchEvent<?> event: key.pollEvents()) {
			        WatchEvent.Kind<?> kind = event.kind();
			        
			        // This key is registered only
			        // for ENTRY_CREATE events,
			        // but an OVERFLOW event can
			        // occur regardless if events
			        // are lost or discarded.
			        if (kind == StandardWatchEventKinds.OVERFLOW) {
			            continue;
			        }

			        // The filename is the
			        // context of the event.
			        WatchEvent<Path> ev = (WatchEvent<Path>)event;
			        Path filename = ev.context();
			        System.out.println("KIND:"+kind+" filename:"+filename);
			        
			        fileListener.fileRecevied("fileReceived",filename.toString(),fileHandler.getInputLocation()+File.separator+filename.toString());
			    }

			    // Reset the key -- this step is critical if you want to
			    // receive further watch events.  If the key is no longer valid,
			    // the directory is inaccessible so exit the loop.
			    boolean valid = key.reset();
			    if (!valid) {
			    	running = false;
			    	System.err.println("ERROR"+valid);
			    }
				
			} else {
				System.out.format("Time's up!%n");
				timer.cancel();
			}
		}
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public FileHandler getFileHandler() {
		return fileHandler;
	}

	public void setFileHandler(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}
	
	
	
	
}
