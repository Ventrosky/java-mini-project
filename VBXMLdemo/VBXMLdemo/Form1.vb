Public Class Form1

    Private xmlDataBase As String = My.Application.Info.DirectoryPath & "\xmlData.xml"

    Private Sub Form1_Load(sender As Object, e As EventArgs) Handles MyBase.Load

        If My.Computer.FileSystem.FileExists(xmlDataBase) = True Then
            DataSet1.ReadXml(xmlDataBase)
        End If
    End Sub

    Private Sub ContactsBindingNavigatorSaveItem_Click(sender As Object, e As EventArgs) Handles ContactsBindingNavigatorSaveItem.Click
        Me.Validate()
        ContactsBindingSource.EndEdit()
        DataSet1.WriteXml(xmlDataBase)
    End Sub
End Class
