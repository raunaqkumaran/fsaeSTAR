"""This takes all the Simulation files specified in the linuxConfig file and periodically runs them everyday"""

import schedule
import time


# Executes folderBuilder.py for Job Scheduler
def job():
    exec(open('folderBuilder.py').read())


# Defines the schedule for the job
schedule.every().day.at("24:00").do(job)

while True:
    schedule.run_pending()
    time.sleep(1)
