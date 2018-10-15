Imports System
Imports System.Collections.Concurrent
Imports System.Data
Imports System.Threading

Module Program

    Public codaTest As New TestConcurrency()
    Public mySyncdQ As New ConcurrentQueue(Of DataRow)
    Public evenCount As Integer = 0
    Public oddCount As Integer = 0

    Sub Main(args As String())
        Console.WriteLine("Hello World!")

        Dim T(999) As Thread

        For I As Integer = 0 To 999
            T(I) = New Thread(AddressOf testThread)
            T(I).Start()
        Next

        Threading.Thread.Sleep(5000)

        Dim nuova As DataTable = codaTest.consumaDataTable()
        Console.WriteLine($"Fine len: {nuova.Rows.Count} after {T.Count}")
        Console.WriteLine($"Even count: {evenCount} - Odd count: {oddCount} - TOT: {evenCount + oddCount}")

    End Sub

    Private Sub testThread()
        Dim table As DataTable = GetTable()
        For Each row As DataRow In table.Rows
            codaTest.addRow(row)
        Next

        Dim number As Integer = randomNumber(100)

        If (number Mod 2 = 0) Then
            Interlocked.Increment(evenCount)
        Else
            Interlocked.Increment(oddCount)
        End If

    End Sub


    Private Function GetTable() As DataTable
        Dim table As New DataTable

        table.Columns.Add("Dosage", GetType(Integer))
        table.Columns.Add("Drug", GetType(String))
        table.Columns.Add("Patient", GetType(String))
        table.Columns.Add("Date", GetType(DateTime))

        table.Rows.Add(25, "Indocin", "David", DateTime.Now)
        table.Rows.Add(50, "Enebrel", "Sam", DateTime.Now)
        table.Rows.Add(10, "Hydralazine", "Christoff", DateTime.Now)
        table.Rows.Add(21, "Combivent", "Janet", DateTime.Now)
        table.Rows.Add(100, "Dilantin", "Melanie", DateTime.Now)

        Return table
    End Function

    Public Function randomNumber(ByVal n As Integer) As Integer
        Dim r As New Random(System.DateTime.Now.Millisecond)

        Return r.Next(1, n)
    End Function

    Public Class TestConcurrency
        Public coda As ConcurrentQueue(Of DataRow)

        Public Sub New()
            coda = New ConcurrentQueue(Of DataRow)
        End Sub

        Public Sub addRow(ByVal riga As DataRow)

            SyncLock coda

                coda.Enqueue(riga)
                Console.WriteLine($"len:{coda.Count}")
            End SyncLock
        End Sub

        Public Function getCount() As Integer
            Return coda.Count
        End Function

        Public Function consumaDataTable() As DataTable
            Dim tabella As New DataTable()
            tabella.Columns.Add("Dosage", GetType(Integer))
            tabella.Columns.Add("Drug", GetType(String))
            tabella.Columns.Add("Patient", GetType(String))
            tabella.Columns.Add("Date", GetType(DateTime))
            Dim riga As DataRow = Nothing
            While (coda.TryDequeue(riga))
                tabella.ImportRow(riga)
            End While
            Return tabella
        End Function
    End Class

End Module
