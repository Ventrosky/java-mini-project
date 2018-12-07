

Imports System.Threading

Class Print
    Public ReadOnly name As String
    Public ReadOnly type As String
    Public ReadOnly nurel As String
    Public document As String
    Public unifyModel As Boolean

    Public Sub New(ByVal name As String, ByVal type As String, ByVal nurel As String)
        MyClass.name = name
        MyClass.type = type
        MyClass.nurel = nurel
        Console.WriteLine($"{name}-{type}-{nurel}")
    End Sub

    Public Sub elaboraXML()
        document = $"({name} - {type} - {nurel})"
        unifyModel = If(CInt(Math.Ceiling(Rnd() * 10)) + 1 > 8, True, False)
    End Sub

End Class


Module LINQ_test

    Public Const MAX As Integer = 8

    Sub Main()
        Dim printList As New List(Of Print)

        Console.WriteLine("[!] Init")

        printList.Add(New Print("REPRE", "DREPRE", "102"))
        printList.Add(New Print("RICOA", "ARICOA", "202"))
        printList.Add(New Print("REPRE", "AREPRE", "100"))
        printList.Add(New Print("REPRE", "AREPRE", "101"))
        printList.Add(New Print("REPRE", "DREPRE", "100"))
        printList.Add(New Print("REPRE", "AREPRE", "102"))
        printList.Add(New Print("REPRE", "DREPRE", "103"))
        printList.Add(New Print("RICOA", "ARICOA", "200"))
        printList.Add(New Print("RICOA", "DRICOA", "103"))
        printList.Add(New Print("RICOA", "ARICOA", "103"))
        printList.Add(New Print("RICOA", "DRICOA", "201"))
        printList.Add(New Print("REPRE", "DREPRE", "101"))

        Console.WriteLine("[!] Extract")

        Dim queryStampe = From p As Print In printList
                          Order By p.type Descending
                          Group By Operazione = p.name, Nurelaz = p.nurel Into GroupPrint = Group

        Dim T(queryStampe.Count) As Thread
        Dim i As Integer = 0
        For Each gruppo In queryStampe
            'Console.WriteLine($"[*] {gruppo.Nurelaz} - {gruppo.Operazione}")
            'For Each stampa In gruppo.GroupPrint
            '    Console.WriteLine($" -> {stampa.name} : {stampa.type}")
            T(i) = New Thread(Sub() myProcess(gruppo.GroupPrint))
            T(i).Start()
            i += 1
            'myProcess(gruppo.GroupPrint)
            'Next
        Next
        Threading.Thread.Sleep(5000)
    End Sub

    Sub myProcess(ByVal o As Object)
        Console.WriteLine("[!] Process")
        Dim gruppo As IEnumerable(Of Print) = TryCast(o, IEnumerable(Of Print))

        For Each stampa In gruppo
            stampa.elaboraXML()
        Next

        If (gruppo.FirstOrDefault(Function(p) p.unifyModel) IsNot Nothing) Then
            Dim unify As String = String.Empty
            For Each stampa In gruppo
                unify = $"{unify}+{stampa.document}"
            Next
            mySummary($"Lettera:{unify}")
        Else
            For Each stampa In gruppo
                mySummary($"Lettera:{stampa.document}")
            Next
        End If

    End Sub

    Sub mySummary(ByVal lettera As String)
        Console.WriteLine(lettera)
    End Sub
End Module
