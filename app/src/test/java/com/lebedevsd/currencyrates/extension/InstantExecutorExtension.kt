package com.lebedevsd.githubviewer.extension

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import java.util.concurrent.Callable

class InstantExecutorExtension : BeforeEachCallback, AfterEachCallback {

    private val mSchedulerInstance = Schedulers.trampoline()
    private val schedulerFunc: (Scheduler) -> Scheduler = { mSchedulerInstance }
    private val schedulerLazyFunc: (Callable<Scheduler>) -> Scheduler = { mSchedulerInstance }


    override fun beforeEach(context: ExtensionContext?) {
        ArchTaskExecutor.getInstance()
            .setDelegate(object : TaskExecutor() {
                override fun executeOnDiskIO(runnable: Runnable) = runnable.run()

                override fun postToMainThread(runnable: Runnable) = runnable.run()

                override fun isMainThread(): Boolean = true
            })
        RxAndroidPlugins.reset()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerLazyFunc)

        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler(schedulerFunc)
        RxJavaPlugins.setNewThreadSchedulerHandler(schedulerFunc)
        RxJavaPlugins.setComputationSchedulerHandler(schedulerFunc)
    }

    override fun afterEach(context: ExtensionContext?) {
        ArchTaskExecutor.getInstance().setDelegate(null)
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
    }
}