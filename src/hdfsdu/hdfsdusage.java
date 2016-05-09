package hdfsdu;

import java.io.IOException;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.Configuration;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RefineryUtilities;
import java.awt.Font;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.apache.commons.io.FileUtils;




public class hdfsdusage extends ApplicationFrame {

	
	static DefaultPieDataset dataset = new DefaultPieDataset();
	static String myTitle;
	
	 public hdfsdusage(String title) {
	        super(title);
	        setContentPane(createDemoPanel());
	    }

	  
	    private static JFreeChart createChart(PieDataset dataset) {
	        
	        JFreeChart chart = ChartFactory.createPieChart(
	            myTitle,  
	            dataset,            
	            true,               
	            true,
	            false
	        );

	        PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} / {2}"));
	        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
	        plot.setNoDataMessage("No data available");
	        plot.setCircular(false);
	        plot.setLabelGap(0.02);
	        return chart;
	        
	    }
	    
	    public static JPanel createDemoPanel() {
	        JFreeChart chart = createChart(dataset);
	        return new ChartPanel(chart);
	    }	
	    

	public static void main(String[] args) throws IOException {
		
		    
	
		if (args.length < 2 ) {
			System.out.println("Usage: username <directory> ");
			System.exit(0);
					
		}
				
		Configuration conf = new Configuration();
		conf.addResource(new Path("/etc/hadoop/conf/core-site.xml"));
	    	conf.addResource(new Path("/etc/hadoop/conf/hdfs-site.xml"));
		conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
	
	    System.setProperty("HADOOP_USER_NAME", args[0].toString());
	    
	    
	    String sr = args[1].toString();
	    Path mysr = new Path(sr);
	    
	    
	    FileSystem fileSystem = FileSystem.get(conf);
	    if (fileSystem.exists(mysr)) {
	    	
	    	FileStatus[] sts= fileSystem.listStatus(mysr);
	    	Path[] allPaths = FileUtil.stat2Paths(sts);
	    	long total=0;
	    	for (Path p: allPaths ) {
	    		 long fssize=0;	
	    	
	    		fssize = fileSystem.getContentSummary(p).getLength();
	    		if (fssize > 0 ) {     			//don't count 0 byte directories
	    			total += fssize;
				String fileSizeDisplay = FileUtils.byteCountToDisplaySize(fssize);	
    				dataset.setValue(p.getName()+" "+fileSizeDisplay,fssize);
  	    			System.out.println(p + " " + fileSizeDisplay);
	    		}
	    		
	    	}
		String totalSizeDisplay = FileUtils.byteCountToDisplaySize(total);
	    	System.out.println("Total is " + totalSizeDisplay);
	    	myTitle=args[1].toString();
		myTitle=myTitle+" Total Usage:"+totalSizeDisplay;
	    	
	    	// Display Pie Chart Graph of Usage 
	    	hdfsdusage demo = new hdfsdusage("HDFS Disk Usage");
			 
	        demo.pack();
	        RefineryUtilities.centerFrameOnScreen(demo);
	        demo.setVisible(true);
	    	
	    
	    	
	     }
    }
  }
	     



