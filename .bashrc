CDLMD_LICENSE_FILE=1999@flex.cd-adapco.com

job_stat ()
{
tail -f /home/rkumaran/fsaeSTAR/Python/slurm-"$1".out
tail -f /home/rkumaran/secondaryGit/fsaeSTAR/Python/slurm-"$1".out
}

export -f job_stat

delete_temp ()
{
find . -name "*.sim~" -type f -delete; du -sh .
}

export -f delete_temp

star()
{
starccm+ -jdebug
}

export -f star

status()
{
    watch -n 10 'squeue -u rkumaran --sort=+i; qlist; squeue -A scholar --sort=+i; squeue -A long --sort=+i; squeue -A gpu --sort=+i'
}

export -f status

storage_space()
{
    du -sh .
}

export -f storage_space

folder_size()
{
    du -sh $(ls -A) | sort -rh
}

export -f folder_size

get_all_jobs()

{
squeue -ho %A -u "$1" | xargs
}

export -f get_all_jobs
