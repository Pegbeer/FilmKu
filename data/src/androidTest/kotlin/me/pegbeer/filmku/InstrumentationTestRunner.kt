import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import me.pegbeer.filmku.TestDataApplication

class InstrumentationTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        classLoader: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(classLoader, TestDataApplication::class.java.name, context)
    }
}