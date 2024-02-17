### 作用：

通过自定义UncaughtExceptionHandler完成Java层的异常处理。出现 crash 时，通过对异常各种信息、app 和系统版本信息等来实现异常匹配。如果异常类型为可控类型(如某个缓存未命中导致NPE或写入时内存已满无法写入)可通过预设拦截器来采取措施，如打印日志、添加日志标识、持久化存储、清除缓存、上传云端等，辅助稳定性的治理。若异常已被解决，则通过Looper兜底机制让线程继续运行。

### 使用

- 在lib_crash_prevention的App类getCrashPortrayConfig()写入预设异常并将App定义为全局Application。

- 支持通过自定义拦截器来增删异常处理功能。
