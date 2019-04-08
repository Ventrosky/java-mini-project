Public Class FileIO

    Public Shared Sub WriteToFile(strToWrite As String)
        Dim stream As IO.StreamWriter = Nothing
        Try
            stream = New IO.StreamWriter(AppDomain.CurrentDomain.BaseDirectory + "\demoFile.txt", True)
            stream.Write(strToWrite)
            stream.Flush()
            stream.Close()
        Catch ex As Exception

        End Try
    End Sub
End Class
