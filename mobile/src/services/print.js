import RNPrint from 'react-native-print';

const printHTML = async (mes, pedidos) => {
  let html_content = 
  `
  <style>
    #title {
      text-align: center;
      font-family: arial, sans-serif;
    }

    #pedidos {
      text-align: center;
      font-family: Arial, sans-serif;
      border-collapse: collapse;
      border: 3px solid #ddd;
      width: 100%;
    }

    #pedidos td, #pedidos th {
      border: 1px solid #ddd;
      padding: 8px;
    }

    #pedidos tr:nth-child(even) {
      background-color: #f2f2f2;
    }

    #pedidos th {
      padding-top: 12px;
      padding-bottom: 12px;
      text-align: center;
      background-color: #4caf50;
      color: #fff;
    }
  </style>
  <div>
    <h1 id='title'>Pedidos do mes de ${mes}</h1>
    <table id='pedidos'>
      <tbody>
        <tr>
          <th>Produto</th>
          <th>Qtd</th>
        </tr>
        ${pedidos.map(pedido => `<tr><td>${pedido[0]}</td><td>${pedido[1]}</td></tr>`)}
      </tbody>
    </table>
  </div>`
  await RNPrint.print({
    html: html_content
  })
}

export default printHTML;