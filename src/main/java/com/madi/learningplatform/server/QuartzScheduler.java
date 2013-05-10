package com.madi.learningplatform.server;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.CronScheduleBuilder.*;

public class QuartzScheduler {
    public static void main(String[] args) throws InterruptedException {

        try {
            // Grab the Scheduler instance from the Factory 
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();

            Logger.getLogger(QuartzScheduler.class).info("Started the quartz scheduler at " +new Date());
            // define the job and tie it to our HelloJob class
            JobDetail job = newJob(NoteReminderUpdater.class)
                .withIdentity("job1", "group1")
                .build();

            // Trigger the job to run now, and then repeat every 40 seconds
            Trigger trigger = newTrigger()
                .withIdentity("trigger_emails", "group_emails")
                .startNow()
                .withSchedule(dailyAtHourAndMinute(7, 0))
                .build();
           
            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);
            
            Thread.sleep(60000);
            
            scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}
