<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet"
	href="https://www.w3schools.com/lib/w3-theme-indigo.css">

<script type="text/javascript">
	function validateAddRowData() {
		if (null == document.getElementById("product").value
				|| "" == document.getElementById("product").value) {
			document.getElementById("product").value = "";
			document.getElementById("product").focus();
			alert("Please select a valid Product Name");
			return false;
		} else if (null == document.getElementById("description").value
				|| "" == document.getElementById("description").value) {
			document.getElementById("description").value = "";
			document.getElementById("description").focus();
			alert("Please provide a valid description");
			return false;
		} else if (isNaN(document.getElementById("quantity").value)
				|| null == document.getElementById("quantity").value
				|| "" == document.getElementById("quantity").value) {
			document.getElementById("quantity").value = "";
			document.getElementById("quantity").focus();
			alert("Please provide a valid quantity");
			return false;
		} else if (isNaN(document.getElementById("unitPrice").value)
				|| null == document.getElementById("unitPrice").value
				|| "" == document.getElementById("unitPrice").value) {
			document.getElementById("unitPrice").value = "";
			document.getElementById("unitPrice").focus();
			alert("Please provide a valid unit-price");
			return false;
		} else if (isNaN(document.getElementById("tax").value)
				|| null == document.getElementById("tax").value
				|| "" == document.getElementById("tax").value) {
			document.getElementById("tax").value = "";
			document.getElementById("tax").focus();
			alert("Please provide a valid tax percentage");
			return false;
		} else if (isNaN(document.getElementById("discount").value)
				|| null == document.getElementById("discount").value
				|| "" == document.getElementById("discount").value) {
			document.getElementById("discount").value = "";
			document.getElementById("discount").focus();
			alert("Please provide a valid discount percentage");
			return false;
		} else if (isNaN(document.getElementById("total").value)
				|| null == document.getElementById("total").value
				|| "" == document.getElementById("total").value) {
			document.getElementById("total").value = "";
			document.getElementById("total").focus();
			alert("Please provide a valid total value");
			return false;
		} else {
			document.getElementById("quantity").value = checkPrecision(document
					.getElementById("quantity"));
			document.getElementById("unitPrice").value = checkPrecision(document
					.getElementById("unitPrice"));
			document.getElementById("discount").value = checkPrecision(document
					.getElementById("discount"));
			document.getElementById("tax").value = checkPrecision(document
					.getElementById("tax"));
			document.getElementById("total").value = checkPrecision(document
					.getElementById("total"));
			return true;
		}

	}
	function GeneratePdf() {
		var str = "";
		var table = document.getElementById("addRowTable");
		for (var r = 1; r < table.rows.length; r++) {
			if (table.rows[r].cells[8].innerHTML != "deleted") {
				str = str + table.rows[r].cells[0].innerHTML + "##"
						+ table.rows[r].cells[1].innerHTML + "##"
						+ table.rows[r].cells[2].innerHTML + "##"
						+ table.rows[r].cells[3].innerHTML + "##"
						+ table.rows[r].cells[4].innerHTML + "##"
						+ table.rows[r].cells[5].innerHTML + "##"
						+ table.rows[r].cells[6].innerHTML + "##"
						+ table.rows[r].cells[7].innerHTML + "##"
						+ table.rows[r].cells[1].id + "@@@";
			}
		}
		if (str == "" || str == null) {
			alert("Please add atleast one product");
			return false;
		} else {
			document.getElementById("pdfTextContent").value = str;
			return true;
		}
	}
</script>
<body>
	<div class="w3-card-4" align="center">

		<div class="w3-container w3-theme w3-card-2">
			<h2>Add New Product</h2>
		</div>
		<form action="addNewProduct" method="post"
			enctype="application/x-www-form-urlencoded">
			${msgToUser}
			<table id="borderManageTable" border="1" class="w3-table w3-striped"
				style="width: 80%">
				<tr>
					<td><table>
							<tr>
								<td>Product</td>
								<td><input type="text" name="product" style="width: 180px" id="product" value=""></td>
								<td>&nbsp;Default Quantity</td>
								<td><input type="text" style="width: 180px" name="quantity" id="quantity"
									value=""></td>
							</tr>
							<tr>
								<td rowspan="3">Description</td>
								<td rowspan="3"><textarea rows="6" cols="20" style="width: 180px"
										name="description" id="description"></textarea></td>
								<td>&nbsp;Unit Price (&#8377;)</td>
								<td><input type="text" style="width: 180px" name="unitPrice" id="unitPrice"
									value=""></td>
							</tr>
							<tr>
								<td>Default Tax (&#8377;)</td>
								<td><input type="text" style="width: 180px"name="tax" id="tax" value=""></td>
							</tr>
							<tr>
								<td>Default Discount (&#8377;)</td>
								<td><input type="text" style="width: 180px" name="discount" id="discount"
									value=""></td>
							</tr>
							<tr>
								<td align="center" colspan="4"><div align="center">
										<input type="submit" name="Add Product" id=" Add Product "
											value="&nbsp;&nbsp;&nbsp;&nbsp;Add Product&nbsp;&nbsp;&nbsp;&nbsp;" />
									</div></td>
							</tr>
						</table></td>
				</tr>
			</table>
		</form>
	</div>
</body>
