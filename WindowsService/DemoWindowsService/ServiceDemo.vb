Public Class ServiceDemo

    Dim tmr As Timers.Timer
    Const msec As Integer = 1000 ' 1 sec
    Const PATH_IN As String = "C:\Temp\input\"
    Const PATH_OUT As String = "C:\Temp\output\"

    ''' 
    ''' Prompt dei comandi per gli sviluppatori per VS 2017
    ''' installutil.exe "DemoWindowsService.exe"
    ''' sc.exe delete "Windows Service Demo"
    ''' 
    Protected Overrides Sub OnStart(ByVal args() As String)
        ' avviare il proprio servizio. 
        tmr = New Timers.Timer
        tmr.Interval = msec
        AddHandler tmr.Elapsed, AddressOf TickHandler
        tmr.Enabled = True

        If (Not IO.Directory.Exists(PATH_IN)) Then
            IO.Directory.CreateDirectory(PATH_IN)
        End If
        If (Not IO.Directory.Exists(PATH_OUT)) Then
            IO.Directory.CreateDirectory(PATH_OUT)
        End If

        FileIO.WriteToFile($"[*] Service Started {vbNewLine}")
    End Sub

    Protected Overrides Sub OnStop()
        ' arrestare il proprio servizio.
        tmr.Enabled = False
        FileIO.WriteToFile($"[*] Service Stopped {vbNewLine}")
    End Sub

    Private Sub TickHandler(obj As Object, e As EventArgs)
        FileIO.WriteToFile($"[*] Tick {Math.Floor(msec / 1000)} Secs {vbNewLine}")
        PollingFolder()
    End Sub

    Private Sub PollingFolder()
        Dim files() As String = IO.Directory.GetFiles(PATH_IN)
        For Each fileName As String In files
            Dim text As String = IO.File.ReadAllText(fileName)

            FileIO.WriteToFile($"[*] Found: {fileName} {vbNewLine}")

            Dim time As DateTime = DateTime.Now
            Dim format As String = "ddMMHHmmyyyy"
            Dim tempName As String = $"{time.ToString(format)}.txt"

            IO.File.Move(fileName, $"{PATH_OUT}{tempName}")
            FileIO.WriteToFile($"[*] Moved: {tempName} {vbNewLine}")
        Next
    End Sub

End Class
