

var botao = document.querySelector("#adicionar-paciente");
botao.addEventListener("click" , function(event){
									event.preventDefault();//IMPEDE COMPORTAMENTO PADRÃO
									
									var campoNome = document.querySelector("#campo-nome");
									var campoPeso = document.querySelector("#campo-peso");
									var campoAltura = document.querySelector("#campo-altura");
									
									var novoPaciente = "<tr class='paciente'>"+
															"<td class='info-nome'>"+ campoNome.value +"</td>"+
															"<td class='info-peso'>"+campoPeso.value+"</td>"+
															"<td class='info-altura'>"+campoAltura.value+"</td>"+
															"<td class='info-imc'>"+campoPeso.value/(campoAltura.value*campoAltura.value)+"</td>"+
														 "</tr>";
														
									var tabela = document.querySelector("table");

									tabela.innerHTML += novoPaciente;
								
									campoNome.value = "";
									campoPeso.value = "";
									campoAltura.value = "";
									});