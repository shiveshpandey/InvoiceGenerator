<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	function onProductChangeValueReset() {
		var count = 0;
		var index = document.getElementById("product").selectedIndex;
		<c:forEach items="${productList}" var="item">
		if (index == count++) {
			document.getElementById("product").value = "${item.product}";
			document.getElementById("description").value = "${item.description}";
			document.getElementById("quantity").value = 1;
			document.getElementById("unitPrice").value = "${item.unitPrice}";
			document.getElementById("tax").value = "${item.tax}";
			document.getElementById("discount").value = "${item.discount}";
			document.getElementById("total").value = "${item.total}";
		}
		</c:forEach>
	}
	function onloadProductListPopulate() {
		var i = 0;
		var product = document.getElementById("product");
		<c:forEach items="${productList}" var="item">
		var option = document.createElement('option');
		option.text = option.value = "${item.product}";
		product.add(option, i++);
		</c:forEach>
	}

	function isInteger(x) {
		return x % 1 === 0;
	}

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
	function checkPrecision(obj) {
		if ((obj.value).lastIndexOf(".") == (obj.value).length - 1)
			return obj.value + "0";
		if ((obj.value).indexOf(".") == 0)
			return "0" + obj.value;
		return obj.value;
	}
	function deleteRow(r) {
		document.getElementById("addRowTable").rows[r].cells[8].innerHTML = "deleted";
	}
	function addRecord() {
		if (validateAddRowData()) {
			if (document.getElementById("noProductMsg"))
				document.getElementById("borderManageTable").deleteRow(5);
			var table = document.getElementById("addRowTable");
			var row = table.insertRow(table.rows.length);

			var cell0 = row.insertCell(0);
			var cell1 = row.insertCell(1);
			var cell2 = row.insertCell(2);
			var cell3 = row.insertCell(3);
			var cell4 = row.insertCell(4);
			var cell5 = row.insertCell(5);
			var cell6 = row.insertCell(6);
			var cell7 = row.insertCell(7);
			var cell8 = row.insertCell(8);

			cell0.innerHTML = table.rows.length - 1;
			cell1.innerHTML = document.getElementById("product").value;
			cell2.innerHTML = document.getElementById("description").value;
			cell3.innerHTML = document.getElementById("quantity").value;
			cell4.innerHTML = document.getElementById("unitPrice").value;
			cell5.innerHTML = document.getElementById("tax").value;
			cell6.innerHTML = document.getElementById("discount").value;
			cell7.innerHTML = document.getElementById("total").value;
			cell8.innerHTML = "<button type='button' onclick='deleteRow(" + '"'
					+ (table.rows.length - 1) + '"' + ")' >Delete</button>";
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
						+ table.rows[r].cells[7].innerHTML + "@@@";
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
	<div class="container-fluid" align="center">
		<h2>
			<u>Invoice/Bill Form</u>
		</h2>
		<form action="generateInvoice" method="post"
			enctype="application/x-www-form-urlencoded">
			<input type="hidden" name="pdfTextContent" id="pdfTextContent"
				value="" />
			<table id="borderManageTable" border="1">
				<tr>
					<td><h4>Seller Details</h4></td>
					<td><h4>Customer Details</h4></td>
				</tr>
				<tr>
					<td>
						<table>
							<tr>
								<td>Name</td>
								<td><input type="text" style="width: 180px"
									name="companyName" id="companyName" value="${companyName}"></td>
							</tr>
							<tr>
								<td>Address</td>
								<td><textarea rows="4" cols="20" name="companyAddress"
										id="companyAddress">${companyAddress}</textarea></td>
							</tr>
							<tr>
								<td>Mobile/Tel</td>
								<td><input type="text" style="width: 180px"
									name="companyMobile" id="companyMobile"
									value="${companyMobile}"></td>
							</tr>
							<tr>
								<td>VAT/TIN #</td>
								<td><input type="text" style="width: 180px"
									name="companyVattin" id="companyVattin"
									value="${companyVattin}"></td>
							</tr>
							<tr>
								<td>CST #</td>
								<td><input type="text" style="width: 180px"
									name="companyCst" id="companyCst" value="${companyCst}"></td>
							</tr>
						</table>
					</td>
					<td>
						<table>
							<tr>
								<td>Name</td>
								<td><input type="text" style="width: 180px"
									name="customerName" id="customerName" value=""></td>
							</tr>
							<tr>
								<td>Address</td>
								<td><textarea rows="4" cols="20" name="customerAddress"
										id="customerAddress"></textarea></td>
							</tr>
							<tr>
								<td>Mobile/Tel</td>
								<td><input type="text" style="width: 180px"
									name="customerMobile" id="customerMobile" value=""></td>
							</tr>
							<tr>
								<td>Email</td>
								<td><input type="text" style="width: 180px"
									name="customerEmail" id="customerEmail" value=""></td>
							</tr>
							<tr>
								<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>&nbsp;Order ID&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
						type="text" style="width: 180px" name="orderId" id="orderId"
						value="${orderId}"></td>
					<td>&nbsp;Order Date&nbsp;<input type="text"
						style="width: 180px" name="orderDate" id="orderDate"
						value="${orderDate}"></td>
				</tr>
				<tr>
					<td colspan="2"><table>
							<tr>
								<td>Product</td>
								<td><select name="product" style="width: 180px"
									id="product" onchange="onProductChangeValueReset();">
								</select></td>
								<td>Quantity</td>
								<td><input type="text" style="width: 180px" name="quantity"
									id="quantity" value=""></td>
							</tr>
							<tr>
								<td rowspan="4">Description</td>
								<td rowspan="4"><textarea rows="4" cols="20"
										name="description" id="description"></textarea></td>
								<td>Unit Price (&#8377;)</td>
								<td><input type="text" style="width: 180px"
									name="unitPrice" id="unitPrice" value=""></td>
							</tr>
							<tr>
								<td>Tax (&#8377;)</td>
								<td><input type="text" style="width: 180px" name="tax"
									id="tax" value=""></td>
							</tr>
							<tr>
								<td>Discount (&#8377;)</td>
								<td><input type="text" style="width: 180px" name="discount"
									id="discount" value=""></td>
							</tr>
							<tr>
								<td>Total (&#8377;)</td>
								<td><input type="text" style="width: 180px" name="total"
									id="total" value=""></td>
							</tr>
							<tr>
								<td align="right" colspan="2"><input type="button"
									name="addRow" id="addRow" value="Add" onClick="addRecord();" /></td>
								<td align="left" colspan="2"><input type="button"
									name="reset" id="reset" value="Reset" /></td>
							</tr>
						</table></td>
				</tr>
				<tr>
					<td colspan="2"><table id="addRowTable" border="1"
							style="width: 100%">
							<thead>
								<tr>
									<th>S No</th>
									<th>Product</th>
									<th>Desc</th>
									<th>Qty</th>
									<th>Price</th>
									<th>Tax</th>
									<th>Discount</th>
									<th>Total</th>
									<th>*</th>
								</tr>
							</thead>
						</table></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><div id="noProductMsg">no
							products added</div></td>
				</tr>
				<tr>
					<td colspan="2">
						<table>
							<tr>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td><input type="submit" name="generateInvoice"
									id="generateInvoice" value="Generate Invoice"
									onClick="return GeneratePdf();" /></td>
								<td><input type="button" name="cancel" id="cancel"
									value="     Reset Form     " /></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<script>
		onloadProductListPopulate();
		onProductChangeValueReset();
	</script>
</body>
