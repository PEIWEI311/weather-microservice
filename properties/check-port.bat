@echo off
echo 正在检查8080端口占用情况...
netstat -ano | findstr :8080
echo.
set /p pid=请输入要结束的进程ID (PID): 
if "%pid%"=="" goto end
echo 正在结束进程ID %pid%...
taskkill /F /PID %pid%
echo 进程已结束。
:end
pause
